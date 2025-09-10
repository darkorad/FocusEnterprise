package com.focusenterprise.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.repositories.*
import com.focusenterprise.services.SalesDataService
import com.focusenterprise.data.SalesRecord
import com.focusenterprise.utils.ExcelUtils
import com.focusenterprise.utils.SalesImportResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class DataManagementViewModel(
    private val customerRepository: CustomerRepository,
    private val invoiceRepository: InvoiceRepository,
    private val paymentRepository: PaymentRepository,
    private val expenseRepository: ExpenseRepository,
    private val stockRepository: StockRepository,
    private val invoiceItemRepository: InvoiceItemRepository
) : ViewModel() {
    
    // Sales data service for aggregating sales records
    private val salesDataService = SalesDataService(
        invoiceRepository = invoiceRepository,
        customerRepository = customerRepository,
        stockRepository = stockRepository,
        invoiceItemRepository = invoiceItemRepository
    )

    private val _exportStatus = MutableStateFlow<ExportStatus>(ExportStatus.Idle)
    val exportStatus: StateFlow<ExportStatus> = _exportStatus.asStateFlow()

    private val _importStatus = MutableStateFlow<ImportStatus>(ImportStatus.Idle)
    val importStatus: StateFlow<ImportStatus> = _importStatus.asStateFlow()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _isExporting = MutableStateFlow(false)
    val isExporting: StateFlow<Boolean> = _isExporting.asStateFlow()

    private val _isImporting = MutableStateFlow(false)
    val isImporting: StateFlow<Boolean> = _isImporting.asStateFlow()

    private val _importSummary = MutableStateFlow("")
    val importSummary: StateFlow<String> = _importSummary.asStateFlow()
    
    // Sales export/import specific states
    private val _salesExportProgress = MutableStateFlow(0)
    val salesExportProgress: StateFlow<Int> = _salesExportProgress.asStateFlow()
    
    private val _salesImportProgress = MutableStateFlow(0)
    val salesImportProgress: StateFlow<Int> = _salesImportProgress.asStateFlow()
    
    private val _isSalesExporting = MutableStateFlow(false)
    val isSalesExporting: StateFlow<Boolean> = _isSalesExporting.asStateFlow()
    
    private val _isSalesImporting = MutableStateFlow(false)
    val isSalesImporting: StateFlow<Boolean> = _isSalesImporting.asStateFlow()
    
    private val _salesImportSummary = MutableStateFlow("")
    val salesImportSummary: StateFlow<String> = _salesImportSummary.asStateFlow()

    // Export all data to Excel with memory optimization
    fun exportDataToExcel(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                _isExporting.value = true
                _isError.value = false
                _statusMessage.value = "Preparing data for export..."

                _statusMessage.value = "Loading data for export..."

                // Collect data with memory management - use chunked approach
                val customers = withContext(Dispatchers.IO) {
                    try {
                        val allCustomers = customerRepository.allCustomers.first()
                        // Limit to prevent memory issues
                        if (allCustomers.size > 2000) {
                            android.util.Log.w("DataManagementViewModel", "Large customer dataset (${allCustomers.size}), limiting to recent 2000")
                            allCustomers.takeLast(2000)
                        } else {
                            allCustomers
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading customers", e)
                        emptyList()
                    }
                }
                
                val invoices = withContext(Dispatchers.IO) {
                    try {
                        val allInvoices = invoiceRepository.allInvoices.first()
                        // Limit to prevent memory issues
                        if (allInvoices.size > 3000) {
                            android.util.Log.w("DataManagementViewModel", "Large invoice dataset (${allInvoices.size}), limiting to recent 3000")
                            allInvoices.takeLast(3000)
                        } else {
                            allInvoices
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading invoices", e)
                        emptyList()
                    }
                }
                
                val payments = withContext(Dispatchers.IO) {
                    try {
                        val allPayments = paymentRepository.allPayments.first()
                        // Limit to prevent memory issues
                        if (allPayments.size > 3000) {
                            android.util.Log.w("DataManagementViewModel", "Large payment dataset (${allPayments.size}), limiting to recent 3000")
                            allPayments.takeLast(3000)
                        } else {
                            allPayments
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading payments", e)
                        emptyList()
                    }
                }
                
                val expenses = withContext(Dispatchers.IO) {
                    try {
                        val allExpenses = expenseRepository.allExpenses.first()
                        // Limit to prevent memory issues
                        if (allExpenses.size > 3000) {
                            android.util.Log.w("DataManagementViewModel", "Large expense dataset (${allExpenses.size}), limiting to recent 3000")
                            allExpenses.takeLast(3000)
                        } else {
                            allExpenses
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading expenses", e)
                        emptyList()
                    }
                }
                
                val stockItems = withContext(Dispatchers.IO) {
                    try {
                        val allStockItems = stockRepository.allStockItems.first()
                        // Limit to prevent memory issues
                        if (allStockItems.size > 2000) {
                            android.util.Log.w("DataManagementViewModel", "Large stock dataset (${allStockItems.size}), limiting to recent 2000")
                            allStockItems.takeLast(2000)
                        } else {
                            allStockItems
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading stock items", e)
                        emptyList()
                    }
                }
                
                val totalRecords = customers.size + invoices.size + payments.size + expenses.size + stockItems.size
                android.util.Log.d("DataManagementViewModel", "Exporting $totalRecords records total")

                _statusMessage.value = "Generating Excel file..."

                // Export using ExcelUtils with error handling
                val success = withContext(Dispatchers.IO) {
                    try {
                        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                            ExcelUtils.exportAllDataToStream(
                                outputStream = outputStream,
                                invoices = invoices,
                                payments = payments,
                                expenses = expenses,
                                stockItems = stockItems,
                                customers = customers
                            )
                        } ?: false
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory during export", e)
                        System.gc() // Force garbage collection
                        false
                    } catch (e: Exception) {
                        android.util.Log.e("DataManagementViewModel", "Export failed", e)
                        false
                    }
                }

                if (success) {
                    _statusMessage.value = "Export completed successfully!"
                    _isError.value = false
                } else {
                    _statusMessage.value = "Export failed. Try reducing data size or free up device memory."
                    _isError.value = true
                }
            } catch (e: OutOfMemoryError) {
                _statusMessage.value = "Export failed: Not enough memory. Please close other apps and try again."
                _isError.value = true
                System.gc()
            } catch (e: Exception) {
                _statusMessage.value = "Export failed: ${e.message}"
                _isError.value = true
            } finally {
                _isExporting.value = false
            }
        }
    }

    // Export sample template
    fun exportSampleTemplate(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                _isExporting.value = true
                _isError.value = false
                _statusMessage.value = "Generating sample template..."
                
                val success = withContext(Dispatchers.IO) {
                    try {
                        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                            ExcelUtils.exportSampleTemplateToStream(outputStream)
                        }
                        true
                    } catch (e: Exception) {
                        false
                    }
                }

                if (success) {
                    _statusMessage.value = "Sample template exported successfully!"
                    _isError.value = false
                } else {
                    _statusMessage.value = "Failed to export sample template."
                    _isError.value = true
                }
            } catch (e: Exception) {
                _statusMessage.value = "Template export failed: ${e.message}"
                _isError.value = true
            } finally {
                _isExporting.value = false
            }
        }
    }

    // Legacy export method for backward compatibility
    fun exportAllData(context: Context) {
        viewModelScope.launch {
            try {
                _isExporting.value = true
                _exportStatus.value = ExportStatus.InProgress
                _statusMessage.value = "Preparing data for export..."

                // Collect data from repositories with memory limits
                val customers = withContext(Dispatchers.IO) {
                    try {
                        val allCustomers = customerRepository.allCustomers.first()
                        // Limit to prevent memory issues
                        if (allCustomers.size > 5000) {
                            android.util.Log.w("DataManagementViewModel", "Large customer dataset (${allCustomers.size}), limiting to recent 5000")
                            allCustomers.takeLast(5000)
                        } else {
                            allCustomers
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading customers", e)
                        emptyList()
                    }
                }
                
                val invoices = withContext(Dispatchers.IO) {
                    try {
                        val allInvoices = invoiceRepository.allInvoices.first()
                        // Limit to prevent memory issues
                        if (allInvoices.size > 3000) {
                            android.util.Log.w("DataManagementViewModel", "Large invoice dataset (${allInvoices.size}), limiting to recent 3000")
                            allInvoices.takeLast(3000)
                        } else {
                            allInvoices
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading invoices", e)
                        emptyList()
                    }
                }
                
                val payments = withContext(Dispatchers.IO) {
                    try {
                        val allPayments = paymentRepository.allPayments.first()
                        // Limit to prevent memory issues
                        if (allPayments.size > 3000) {
                            android.util.Log.w("DataManagementViewModel", "Large payment dataset (${allPayments.size}), limiting to recent 3000")
                            allPayments.takeLast(3000)
                        } else {
                            allPayments
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading payments", e)
                        emptyList()
                    }
                }
                
                val expenses = withContext(Dispatchers.IO) {
                    try {
                        val allExpenses = expenseRepository.allExpenses.first()
                        // Limit to prevent memory issues
                        if (allExpenses.size > 3000) {
                            android.util.Log.w("DataManagementViewModel", "Large expense dataset (${allExpenses.size}), limiting to recent 3000")
                            allExpenses.takeLast(3000)
                        } else {
                            allExpenses
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading expenses", e)
                        emptyList()
                    }
                }
                
                val stockItems = withContext(Dispatchers.IO) {
                    try {
                        val allStockItems = stockRepository.allStockItems.first()
                        // Limit to prevent memory issues
                        if (allStockItems.size > 2000) {
                            android.util.Log.w("DataManagementViewModel", "Large stock dataset (${allStockItems.size}), limiting to recent 2000")
                            allStockItems.takeLast(2000)
                        } else {
                            allStockItems
                        }
                    } catch (e: OutOfMemoryError) {
                        android.util.Log.e("DataManagementViewModel", "Out of memory loading stock items", e)
                        emptyList()
                    }
                }

                _statusMessage.value = "Generating Excel file..."

                // Generate filename with current date
                val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                val fileName = "FocusEnterprise_Export_${dateFormat.format(Date())}.xlsx"

                // Export using ExcelUtils
                val success = withContext(Dispatchers.IO) {
                    ExcelUtils.exportAllDataByMonth(
                        context = context,
                        fileName = fileName,
                        invoices = invoices,
                        payments = payments,
                        expenses = expenses,
                        stockItems = stockItems,
                        customers = customers
                    )
                }

                if (success) {
                    _exportStatus.value = ExportStatus.Success(fileName)
                    _statusMessage.value = "Export completed successfully! File saved to Downloads."
                } else {
                    _exportStatus.value = ExportStatus.Error("Failed to export data")
                    _statusMessage.value = "Export failed. Please try again."
                }
            } catch (e: Exception) {
                _exportStatus.value = ExportStatus.Error(e.message ?: "Unknown error")
                _statusMessage.value = "Export failed: ${e.message}"
            } finally {
                _isExporting.value = false
            }
        }
    }

    // Import data from Excel file
    fun importDataFromExcel(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                _isImporting.value = true
                _importStatus.value = ImportStatus.InProgress
                _isError.value = false
                _statusMessage.value = "Reading Excel file..."

                // Get existing data for validation
                val existingCustomers = customerRepository.allCustomers.first()
                val existingInvoices = invoiceRepository.allInvoices.first()

                _statusMessage.value = "Validating data..."

                // Import using ExcelUtils
                val importResult = withContext(Dispatchers.IO) {
                    ExcelUtils.importAllDataFromExcel(
                        context = context,
                        uri = uri,
                        existingCustomers = existingCustomers,
                        existingInvoices = existingInvoices
                    )
                }

                if (importResult.error != null) {
                    _importStatus.value = ImportStatus.Error(importResult.error!!)
                    _statusMessage.value = "Import failed: ${importResult.error}"
                    _isError.value = true
                    return@launch
                }

                _statusMessage.value = "Saving data to database..."

                // Save imported data to database
                var importedCount = 0
                var skippedCount = 0

                // Insert customers first
                importResult.customers.forEach { customer ->
                    try {
                        customerRepository.insert(customer)
                        importedCount++
                    } catch (e: Exception) {
                        skippedCount++
                    }
                }

                // Insert invoices
                importResult.invoices.forEach { invoice ->
                    try {
                        invoiceRepository.insertInvoiceWithItems(invoice, emptyList())
                        importedCount++
                    } catch (e: Exception) {
                        skippedCount++
                    }
                }

                // Insert payments
                importResult.payments.forEach { payment ->
                    try {
                        paymentRepository.insert(payment)
                        importedCount++
                    } catch (e: Exception) {
                        skippedCount++
                    }
                }

                // Insert expenses
                importResult.expenses.forEach { expense ->
                    try {
                        expenseRepository.insert(expense)
                        importedCount++
                    } catch (e: Exception) {
                        skippedCount++
                    }
                }

                // Insert stock items
                importResult.stockItems.forEach { stockItem ->
                    try {
                        stockRepository.insert(stockItem)
                        importedCount++
                    } catch (e: Exception) {
                        skippedCount++
                    }
                }

                val totalRecords = importResult.customers.size + importResult.invoices.size + 
                                 importResult.payments.size + importResult.expenses.size + 
                                 importResult.stockItems.size

                _importStatus.value = ImportStatus.Success(
                    ImportSummary(
                        totalRecords = totalRecords,
                        importedRecords = importedCount,
                        skippedRecords = skippedCount,
                        customers = importResult.customers.size,
                        invoices = importResult.invoices.size,
                        payments = importResult.payments.size,
                        expenses = importResult.expenses.size,
                        stockItems = importResult.stockItems.size
                    )
                )
                
                _statusMessage.value = "Import completed! $importedCount records imported, $skippedCount skipped."
                _importSummary.value = "Total: $totalRecords | Imported: $importedCount | Skipped: $skippedCount\n" +
                                      "Customers: ${importResult.customers.size} | Invoices: ${importResult.invoices.size} | " +
                                      "Payments: ${importResult.payments.size} | Expenses: ${importResult.expenses.size} | " +
                                      "Stock Items: ${importResult.stockItems.size}"
                _isError.value = false

            } catch (e: Exception) {
                _importStatus.value = ImportStatus.Error(e.message ?: "Unknown error")
                _statusMessage.value = "Import failed: ${e.message}"
                _isError.value = true
            } finally {
                _isImporting.value = false
            }
        }
    }



    // Clear status messages
    fun clearStatus() {
        _statusMessage.value = ""
        _importSummary.value = ""
        _isError.value = false
    }

    // Reset export status
    fun resetExportStatus() {
        _exportStatus.value = ExportStatus.Idle
    }

    // Reset import status
    fun resetImportStatus() {
        _importStatus.value = ImportStatus.Idle
    }
    
    /**
     * Export sales data to Excel with progress tracking
     * Crash-proof implementation for large datasets
     */
    fun exportSalesDataToExcel(context: Context, uri: Uri, startDate: Long = 0L, endDate: Long = System.currentTimeMillis()) {
        viewModelScope.launch {
            try {
                _isSalesExporting.value = true
                _salesExportProgress.value = 0
                _isError.value = false
                _statusMessage.value = "Preparing sales data for export..."
                
                // Get sales records with chunked processing for memory efficiency
                val salesRecords = withContext(Dispatchers.IO) {
                    salesDataService.getSalesRecordsChunked(startDate, endDate)
                }
                
                _statusMessage.value = "Exporting ${salesRecords.size} sales records..."
                
                // Export to Excel with progress tracking
                val success = withContext(Dispatchers.IO) {
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        ExcelUtils.exportSalesDataToStream(
                            outputStream = outputStream,
                            salesRecords = salesRecords,
                            onProgress = { processed, total ->
                                val progress = if (total > 0) (processed * 100) / total else 0
                                _salesExportProgress.value = progress
                            }
                        )
                    } ?: false
                }
                
                if (success) {
                    _statusMessage.value = "Sales data exported successfully! ${salesRecords.size} records exported."
                    _salesExportProgress.value = 100
                    _isError.value = false
                } else {
                    _statusMessage.value = "Failed to export sales data"
                    _isError.value = true
                }
                
            } catch (e: OutOfMemoryError) {
                android.util.Log.e("DataManagementViewModel", "Out of memory during sales export", e)
                _statusMessage.value = "Export failed: Not enough memory. Try exporting a smaller date range."
                _isError.value = true
                System.gc()
            } catch (e: Exception) {
                android.util.Log.e("DataManagementViewModel", "Sales export failed", e)
                _statusMessage.value = "Export failed: ${e.message}"
                _isError.value = true
            } finally {
                _isSalesExporting.value = false
            }
        }
    }
    
    /**
     * Import sales data from Excel with validation and progress tracking
     */
    fun importSalesDataFromExcel(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                _isSalesImporting.value = true
                _salesImportProgress.value = 0
                _isError.value = false
                _statusMessage.value = "Reading sales data from Excel..."
                
                val importResult = withContext(Dispatchers.IO) {
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        ExcelUtils.importSalesDataFromStream(
                            inputStream = inputStream,
                            onProgress = { processed, total ->
                                val progress = if (total > 0) (processed * 100) / total else 0
                                _salesImportProgress.value = progress
                            }
                        )
                    } ?: SalesImportResult(emptyList(), 0, 0, listOf("Failed to read file"))
                }
                
                if (importResult.errors.isNotEmpty() && importResult.salesRecords.isEmpty()) {
                    _statusMessage.value = "Import failed: ${importResult.errors.first()}"
                    _salesImportSummary.value = "Errors: ${importResult.errors.joinToString(", ")}"
                    _isError.value = true
                    return@launch
                }
                
                _statusMessage.value = "Processing imported sales data..."
                
                // Convert sales records to database entities
                val (invoices, invoiceItems, newCustomers) = withContext(Dispatchers.IO) {
                    salesDataService.convertToEntities(importResult.salesRecords)
                }
                
                // Insert new customers first
                var insertedCustomers = 0
                var insertedInvoices = 0
                var insertedItems = 0
                var skippedRecords = 0
                
                withContext(Dispatchers.IO) {
                    // Insert new customers
                    newCustomers.forEach { customer ->
                        try {
                            customerRepository.insert(customer)
                            insertedCustomers++
                        } catch (e: Exception) {
                            android.util.Log.w("DataManagementViewModel", "Failed to insert customer: ${e.message}")
                            skippedRecords++
                        }
                    }
                    
                    // Insert invoices with items
                    invoices.forEachIndexed { index, invoice ->
                        try {
                            val relatedItems = invoiceItems.filter { it.invoiceId == 0 } // Items for this invoice
                            invoiceRepository.insertInvoiceWithItems(invoice, relatedItems)
                            insertedInvoices++
                            insertedItems += relatedItems.size
                        } catch (e: Exception) {
                            android.util.Log.w("DataManagementViewModel", "Failed to insert invoice: ${e.message}")
                            skippedRecords++
                        }
                    }
                }
                
                val totalProcessed = insertedCustomers + insertedInvoices + insertedItems
                val totalSkipped = importResult.skippedCount + skippedRecords
                
                _statusMessage.value = "Sales data import completed! $totalProcessed records imported, $totalSkipped skipped."
                _salesImportSummary.value = "Imported: ${importResult.importedCount} sales records\n" +
                                           "New Customers: $insertedCustomers\n" +
                                           "New Invoices: $insertedInvoices\n" +
                                           "Skipped: $totalSkipped\n" +
                                           if (importResult.errors.isNotEmpty()) "Errors: ${importResult.errors.size}" else ""
                _salesImportProgress.value = 100
                _isError.value = false
                
            } catch (e: Exception) {
                android.util.Log.e("DataManagementViewModel", "Sales import failed", e)
                _statusMessage.value = "Import failed: ${e.message}"
                _isError.value = true
            } finally {
                _isSalesImporting.value = false
            }
        }
    }
    
    /**
     * Generate auto filename for sales export
     */
    fun generateSalesExportFileName(): String {
        return ExcelUtils.generateSalesExportFileName()
    }
    
    /**
     * Clear sales-specific status messages
     */
    fun clearSalesStatus() {
        _salesImportSummary.value = ""
        _salesExportProgress.value = 0
        _salesImportProgress.value = 0
    }
}

// Sealed classes for status management
sealed class ExportStatus {
    object Idle : ExportStatus()
    object InProgress : ExportStatus()
    data class Success(val fileName: String) : ExportStatus()
    data class Error(val message: String) : ExportStatus()
}

sealed class ImportStatus {
    object Idle : ImportStatus()
    object InProgress : ImportStatus()
    data class Success(val summary: ImportSummary) : ImportStatus()
    data class Error(val message: String) : ImportStatus()
}

data class ImportSummary(
    val totalRecords: Int,
    val importedRecords: Int,
    val skippedRecords: Int,
    val customers: Int,
    val invoices: Int,
    val payments: Int,
    val expenses: Int,
    val stockItems: Int
)