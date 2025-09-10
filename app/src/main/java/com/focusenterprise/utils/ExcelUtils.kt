package com.focusenterprise.utils

import android.content.Context
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.focusenterprise.data.*
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar

/**
 * Result class for sales import operations
 */
data class SalesImportResult(
    val salesRecords: List<SalesRecord>,
    val importedCount: Int,
    val skippedCount: Int,
    val errors: List<String>
)

class ExcelUtils {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        private val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        
        // Export all data organized by month
        fun exportAllDataByMonth(
            context: Context,
            fileName: String,
            invoices: List<Invoice>,
            payments: List<Payment>,
            expenses: List<Expense>,
            stockItems: List<StockItem>,
            customers: List<Customer>
        ): Boolean {
            return try {
                val workbook = XSSFWorkbook()
                
                // Group data by month
                val invoicesByMonth = groupInvoicesByMonth(invoices)
                val paymentsByMonth = groupPaymentsByMonth(payments)
                val expensesByMonth = groupExpensesByMonth(expenses)
                
                // Create sheets for each month with data
                val allMonths = (invoicesByMonth.keys + paymentsByMonth.keys + expensesByMonth.keys).distinct().sorted()
                
                for (monthKey in allMonths) {
                    val monthName = getMonthName(monthKey)
                    createMonthlyDataSheet(
                        workbook, 
                        monthName,
                        invoicesByMonth[monthKey] ?: emptyList(),
                        paymentsByMonth[monthKey] ?: emptyList(),
                        expensesByMonth[monthKey] ?: emptyList(),
                        customers
                    )
                }
        
        /**
         * Export sales data with proper column headers and progress tracking
         * Crash-proof implementation for large datasets
         */
        fun exportSalesDataToStream(
            outputStream: OutputStream,
            salesRecords: List<SalesRecord>,
            onProgress: ((Int, Int) -> Unit)? = null
        ): Boolean {
            return try {
                android.util.Log.d("ExcelUtils", "Exporting ${salesRecords.size} sales records")
                
                val workbook = XSSFWorkbook()
                
                try {
                    // Create header style
                    val headerStyle = workbook.createCellStyle()
                    val headerFont = workbook.createFont()
                    headerFont.bold = true
                    headerStyle.setFont(headerFont)
                    
                    // Create sales data sheet
                    val sheet = workbook.createSheet("Sales Data")
                    
                    // Create headers as specified in requirements
                    val headerRow = sheet.createRow(0)
                    val headers = arrayOf("Date", "CustomerName", "Product", "Quantity", "Price", "Total")
                    headers.forEachIndexed { index, header ->
                        val cell = headerRow.createCell(index)
                        cell.setCellValue(header)
                        cell.setCellStyle(headerStyle)
                    }
                    
                    // Process data in chunks to prevent memory issues
                    val chunkSize = 500
                    var processedCount = 0
                    
                    salesRecords.chunked(chunkSize).forEachIndexed { chunkIndex, chunk ->
                        chunk.forEachIndexed { recordIndex, record ->
                            try {
                                val rowIndex = processedCount + recordIndex + 1
                                val row = sheet.createRow(rowIndex)
                                
                                // Validate and set data with null/invalid value handling
                                row.createCell(0).setCellValue(record.getFormattedDate())
                                row.createCell(1).setCellValue(record.customerName.takeIf { it.isNotBlank() } ?: "Unknown")
                                row.createCell(2).setCellValue(record.product.takeIf { it.isNotBlank() } ?: "Unknown")
                                row.createCell(3).setCellValue(if (record.quantity > 0) record.quantity.toDouble() else 0.0)
                                row.createCell(4).setCellValue(if (record.price >= 0) record.price else 0.0)
                                row.createCell(5).setCellValue(if (record.total >= 0) record.total else 0.0)
                                
                            } catch (e: Exception) {
                                android.util.Log.w("ExcelUtils", "Error processing record at index ${processedCount + recordIndex}: ${e.message}")
                                // Continue with next record instead of crashing
                            }
                        }
                        
                        processedCount += chunk.size
                        
                        // Report progress
                        onProgress?.invoke(processedCount, salesRecords.size)
                        
                        // Force garbage collection between chunks for large datasets
                        if (salesRecords.size > 5000 && chunkIndex % 10 == 0) {
                            System.gc()
                        }
                    }
                    
                    // Auto-size columns
                    for (i in 0..5) {
                        try {
                            sheet.autoSizeColumn(i)
                        } catch (e: Exception) {
                            // Continue if auto-sizing fails
                            android.util.Log.w("ExcelUtils", "Failed to auto-size column $i: ${e.message}")
                        }
                    }
                    
                    // Write to output stream
                    workbook.write(outputStream)
                    outputStream.flush()
                    
                    android.util.Log.d("ExcelUtils", "Successfully exported $processedCount sales records")
                    true
                    
                } finally {
                    workbook.close()
                }
                
            } catch (e: OutOfMemoryError) {
                android.util.Log.e("ExcelUtils", "Out of memory during sales export: ${e.message}", e)
                System.gc()
                false
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to export sales data: ${e.message}", e)
                false
            }
        }
        
        /**
         * Generate auto filename for sales export
         */
        fun generateSalesExportFileName(): String {
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            return "SalesData_${dateFormat.format(Date())}.xlsx"
        }
        
        /**
         * Import sales data from Excel with header validation
         */
        fun importSalesDataFromStream(
            inputStream: InputStream,
            onProgress: ((Int, Int) -> Unit)? = null
        ): SalesImportResult {
            return try {
                val workbook = WorkbookFactory.create(inputStream)
                val sheet = workbook.getSheetAt(0)
                
                val expectedHeaders = arrayOf("Date", "CustomerName", "Product", "Quantity", "Price", "Total")
                val salesRecords = mutableListOf<SalesRecord>()
                val errors = mutableListOf<String>()
                
                // Validate headers
                val headerRow = sheet.getRow(0)
                if (headerRow == null) {
                    return SalesImportResult(emptyList(), 0, 1, listOf("No header row found"))
                }
                
                val actualHeaders = (0 until headerRow.lastCellNum).map { 
                    headerRow.getCell(it)?.stringCellValue?.trim() ?: ""
                }
                
                if (!expectedHeaders.contentEquals(actualHeaders.toTypedArray())) {
                    return SalesImportResult(
                        emptyList(), 0, 1, 
                        listOf("Header mismatch. Expected: ${expectedHeaders.joinToString()}, Found: ${actualHeaders.joinToString()}")
                    )
                }
                
                val totalRows = sheet.lastRowNum
                var processedRows = 0
                var skippedRows = 0
                
                // Process data rows
                for (rowIndex in 1..totalRows) {
                    try {
                        val row = sheet.getRow(rowIndex)
                        if (row == null) {
                            skippedRows++
                            continue
                        }
                        
                        val salesRecord = SalesRecord.fromExcelRow(row)
                        if (salesRecord.isValid()) {
                            salesRecords.add(salesRecord)
                            processedRows++
                        } else {
                            skippedRows++
                            errors.add("Row ${rowIndex + 1}: Invalid data")
                        }
                        
                        // Report progress
                        onProgress?.invoke(rowIndex, totalRows)
                        
                    } catch (e: Exception) {
                        skippedRows++
                        errors.add("Row ${rowIndex + 1}: ${e.message}")
                    }
                }
                
                workbook.close()
                
                SalesImportResult(salesRecords, processedRows, skippedRows, errors)
                
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to import sales data: ${e.message}", e)
                SalesImportResult(emptyList(), 0, 1, listOf("Import failed: ${e.message}"))
            }
        }
        


        fun exportAllDataByMonth(
            context: Context,
            invoices: List<Invoice>,
            payments: List<Payment>,
            expenses: List<Expense>,
            stockItems: List<StockItem>,
            customers: List<Customer>,
            fileName: String
        ): Boolean {
            return try {
                val workbook = XSSFWorkbook()
                
                // Create summary sheet
                createSummarySheet(workbook, invoices, payments, expenses)
                
                // Create stock items sheet
                createStockItemsSheet(workbook, stockItems)
                
                // Create customers sheet
                createCustomersSheet(workbook, customers)
                
                // Save file using scoped storage
                saveWorkbookToDownloads(context, workbook, fileName)
                workbook.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
                workbook.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
         
         // Helper functions for grouping data by month
         private fun groupInvoicesByMonth(invoices: List<Invoice>): Map<String, List<Invoice>> {
             return invoices.groupBy { getMonthKey(it.date) }
         }
         
         private fun groupPaymentsByMonth(payments: List<Payment>): Map<String, List<Payment>> {
             return payments.groupBy { getMonthKey(it.date) }
         }
         
         private fun groupExpensesByMonth(expenses: List<Expense>): Map<String, List<Expense>> {
             return expenses.groupBy { getMonthKey(it.date) }
         }
         
         private fun getMonthKey(timestamp: Long): String {
             val calendar = Calendar.getInstance()
             calendar.timeInMillis = timestamp
             return "${calendar.get(Calendar.YEAR)}-${String.format("%02d", calendar.get(Calendar.MONTH) + 1)}"
         }
         
         private fun getMonthName(monthKey: String): String {
             val parts = monthKey.split("-")
             val calendar = Calendar.getInstance()
             calendar.set(Calendar.YEAR, parts[0].toInt())
             calendar.set(Calendar.MONTH, parts[1].toInt() - 1)
             return monthYearFormat.format(calendar.time)
         }
         
         // Create monthly data sheet with all data types
         private fun createMonthlyDataSheet(
             workbook: XSSFWorkbook,
             monthName: String,
             invoices: List<Invoice>,
             payments: List<Payment>,
             expenses: List<Expense>,
             customers: List<Customer>
         ) {
             val sheet = workbook.createSheet(monthName)
             var currentRow = 0
             
             // Create header style
             val headerStyle = workbook.createCellStyle()
             val headerFont = workbook.createFont()
             headerFont.bold = true
             headerStyle.setFont(headerFont)
             
             // Add invoices section
             if (invoices.isNotEmpty()) {
                 currentRow = addInvoicesSection(sheet, invoices, customers, currentRow, headerStyle)
                 currentRow += 2 // Add spacing
             }
             
             // Add payments section
             if (payments.isNotEmpty()) {
                 currentRow = addPaymentsSection(sheet, payments, invoices, customers, currentRow, headerStyle)
                 currentRow += 2 // Add spacing
             }
             
             // Add expenses section
             if (expenses.isNotEmpty()) {
                 addExpensesSection(sheet, expenses, currentRow, headerStyle)
             }
             
             // Auto-size columns
             for (i in 0..10) {
                 sheet.autoSizeColumn(i)
             }
         }
          
          // Add invoices section to sheet
          private fun addInvoicesSection(
              sheet: Sheet,
              invoices: List<Invoice>,
              customers: List<Customer>,
              startRow: Int,
              headerStyle: CellStyle
          ): Int {
              var currentRow = startRow
              
              // Section title
              val titleRow = sheet.createRow(currentRow++)
              val titleCell = titleRow.createCell(0)
              titleCell.setCellValue("INVOICES")
              titleCell.setCellStyle(headerStyle)
              
              // Headers
              val headerRow = sheet.createRow(currentRow++)
              val headers = arrayOf("Invoice ID", "Customer", "Date", "Total Amount", "Paid Amount", "Status")
              headers.forEachIndexed { index, header ->
                  val cell = headerRow.createCell(index)
                  cell.setCellValue(header)
                  cell.setCellStyle(headerStyle)
              }
              
              // Data rows
              invoices.forEach { invoice ->
                  val row = sheet.createRow(currentRow++)
                  val customer = customers.find { it.customerId == invoice.customerId }
                  
                  row.createCell(0).setCellValue(invoice.invoiceId.toDouble())
                  row.createCell(1).setCellValue(customer?.name ?: "Unknown")
                  row.createCell(2).setCellValue(dateFormat.format(Date(invoice.date)))
                  row.createCell(3).setCellValue(invoice.totalAmount)
                  row.createCell(4).setCellValue(invoice.paidAmount)
                  row.createCell(5).setCellValue(invoice.status)
              }
              
              return currentRow
          }
          
          // Add payments section to sheet
          private fun addPaymentsSection(
              sheet: Sheet,
              payments: List<Payment>,
              invoices: List<Invoice>,
              customers: List<Customer>,
              startRow: Int,
              headerStyle: CellStyle
          ): Int {
              var currentRow = startRow
              
              // Section title
              val titleRow = sheet.createRow(currentRow++)
              val titleCell = titleRow.createCell(0)
              titleCell.setCellValue("PAYMENTS")
              titleCell.setCellStyle(headerStyle)
              
              // Headers
              val headerRow = sheet.createRow(currentRow++)
              val headers = arrayOf("Payment ID", "Invoice ID", "Customer", "Amount", "Date")
              headers.forEachIndexed { index, header ->
                  val cell = headerRow.createCell(index)
                  cell.setCellValue(header)
                  cell.setCellStyle(headerStyle)
              }
              
              // Data rows
              payments.forEach { payment ->
                  val row = sheet.createRow(currentRow++)
                  val invoice = invoices.find { it.invoiceId == payment.invoiceId }
                  val customer = invoice?.let { customers.find { c -> c.customerId == it.customerId } }
                  
                  row.createCell(0).setCellValue(payment.paymentId.toDouble())
                  row.createCell(1).setCellValue(payment.invoiceId.toDouble())
                  row.createCell(2).setCellValue(customer?.name ?: "Unknown")
                  row.createCell(3).setCellValue(payment.amount)
                  row.createCell(4).setCellValue(dateFormat.format(Date(payment.date)))
              }
              
              return currentRow
          }
          
          // Add expenses section to sheet
          private fun addExpensesSection(
              sheet: Sheet,
              expenses: List<Expense>,
              startRow: Int,
              headerStyle: CellStyle
          ): Int {
              var currentRow = startRow
              
              // Section title
              val titleRow = sheet.createRow(currentRow++)
              val titleCell = titleRow.createCell(0)
              titleCell.setCellValue("EXPENSES")
              titleCell.setCellStyle(headerStyle)
              
              // Headers
              val headerRow = sheet.createRow(currentRow++)
              val headers = arrayOf("Expense ID", "Category", "Amount", "Date", "Notes")
              headers.forEachIndexed { index, header ->
                  val cell = headerRow.createCell(index)
                  cell.setCellValue(header)
                  cell.setCellStyle(headerStyle)
              }
              
              // Data rows
              expenses.forEach { expense ->
                  val row = sheet.createRow(currentRow++)
                  row.createCell(0).setCellValue(expense.expenseId.toDouble())
                  row.createCell(1).setCellValue(expense.category)
                  row.createCell(2).setCellValue(expense.amount)
                  row.createCell(3).setCellValue(dateFormat.format(Date(expense.date)))
                  row.createCell(4).setCellValue(expense.notes ?: "")
              }
              
              return currentRow
          }
           
           // Create summary sheet with totals
           private fun createSummarySheet(
               workbook: XSSFWorkbook,
               invoices: List<Invoice>,
               @Suppress("UNUSED_PARAMETER") payments: List<Payment>,
               expenses: List<Expense>
           ) {
               val sheet = workbook.createSheet("Summary")
               
               // Create header style
               val headerStyle = workbook.createCellStyle()
               val headerFont = workbook.createFont()
               headerFont.bold = true
               headerStyle.setFont(headerFont)
               
               var currentRow = 0
               
               // Title
               val titleRow = sheet.createRow(currentRow++)
               val titleCell = titleRow.createCell(0)
               titleCell.setCellValue("FINANCIAL SUMMARY")
               titleCell.setCellStyle(headerStyle)
               // Add spacing between sections
               
               // Invoice totals
               val invoiceTotalRow = sheet.createRow(currentRow++)
               invoiceTotalRow.createCell(0).setCellValue("Total Invoice Amount:")
               invoiceTotalRow.createCell(1).setCellValue(invoices.sumOf { it.totalAmount })
               
               val paidTotalRow = sheet.createRow(currentRow++)
               paidTotalRow.createCell(0).setCellValue("Total Paid Amount:")
               paidTotalRow.createCell(1).setCellValue(invoices.sumOf { it.paidAmount })
               
               val outstandingRow = sheet.createRow(currentRow++)
               outstandingRow.createCell(0).setCellValue("Outstanding Amount:")
               outstandingRow.createCell(1).setCellValue(invoices.sumOf { it.totalAmount - it.paidAmount })
               
               currentRow++
               
               // Expense totals
               val expenseTotalRow = sheet.createRow(currentRow++)
               expenseTotalRow.createCell(0).setCellValue("Total Expenses:")
               expenseTotalRow.createCell(1).setCellValue(expenses.sumOf { it.amount })
               
               // Net profit
               val netProfitRow = sheet.createRow(currentRow)
               netProfitRow.createCell(0).setCellValue("Net Profit:")
               netProfitRow.createCell(1).setCellValue(invoices.sumOf { it.paidAmount } - expenses.sumOf { it.amount })
               
               // Auto-size columns
               sheet.autoSizeColumn(0)
               sheet.autoSizeColumn(1)
           }
           
           // Create stock items sheet
           private fun createStockItemsSheet(workbook: XSSFWorkbook, stockItems: List<StockItem>) {
               val sheet = workbook.createSheet("Stock Items")
               
               // Create header style
               val headerStyle = workbook.createCellStyle()
               val headerFont = workbook.createFont()
               headerFont.bold = true
               headerStyle.setFont(headerFont)
               
               // Headers
               val headerRow = sheet.createRow(0)
               val headers = arrayOf("Stock ID", "Name", "Purchase Price", "Selling Price", "Quantity", "Total Value")
               headers.forEachIndexed { index, header ->
                   val cell = headerRow.createCell(index)
                   cell.setCellValue(header)
                   cell.setCellStyle(headerStyle)
               }
               
               // Data rows
               stockItems.forEachIndexed { index, item ->
                   val row = sheet.createRow(index + 1)
                   row.createCell(0).setCellValue(item.stockId.toDouble())
                   row.createCell(1).setCellValue(item.name)
                   row.createCell(2).setCellValue(item.purchasePrice)
                   row.createCell(3).setCellValue(item.sellingPrice)
                   row.createCell(4).setCellValue(item.quantity.toDouble())
                   row.createCell(5).setCellValue(item.sellingPrice * item.quantity)
               }
               
               // Auto-size columns
               for (i in 0..5) {
                   sheet.autoSizeColumn(i)
               }
           }
           
           // Create customers sheet
           private fun createCustomersSheet(workbook: XSSFWorkbook, customers: List<Customer>) {
               val sheet = workbook.createSheet("Customers")
               
               // Create header style
               val headerStyle = workbook.createCellStyle()
               val headerFont = workbook.createFont()
               headerFont.bold = true
               headerStyle.setFont(headerFont)
               
               // Headers
               val headerRow = sheet.createRow(0)
               val headers = arrayOf("Customer ID", "Name", "PIB", "Phone", "Email", "Address", "Total Debt")
               headers.forEachIndexed { index, header ->
                   val cell = headerRow.createCell(index)
                   cell.setCellValue(header)
                   cell.setCellStyle(headerStyle)
               }
               
               // Data rows
               customers.forEachIndexed { index, customer ->
                   val row = sheet.createRow(index + 1)
                   row.createCell(0).setCellValue(customer.customerId.toDouble())
                   row.createCell(1).setCellValue(customer.name)
                   row.createCell(2).setCellValue(customer.pib ?: "")
                   row.createCell(3).setCellValue(customer.phone)
                   row.createCell(4).setCellValue(customer.email ?: "")
                   row.createCell(5).setCellValue(customer.address)
                   row.createCell(6).setCellValue(customer.totalDebt)
               }
               
               // Auto-size columns
               for (i in 0..6) {
                   sheet.autoSizeColumn(i)
               }
           }
           
           fun createInvoiceTemplate(context: Context, fileName: String): Boolean {
            return try {
                val workbook = XSSFWorkbook()
                val sheet = workbook.createSheet("Invoice Template")
                
                // Create header row
                val headerRow = sheet.createRow(0)
                val headers = arrayOf(
                    "Customer Name",
                    "Customer PIB",
                    "Customer Phone",
                    "Customer Email",
                    "Customer Address",
                    "Invoice Date (YYYY-MM-DD)",
                    "Total Amount",
                    "Paid Amount",
                    "Status (pending/partial/paid)"
                )
                
                headers.forEachIndexed { index, header ->
                    val cell = headerRow.createCell(index)
                    cell.setCellValue(header)
                    
                    // Style header cells
                    val cellStyle = workbook.createCellStyle()
                    val font = workbook.createFont()
                    font.bold = true
                    cellStyle.setFont(font)
                    cell.setCellStyle(cellStyle)
                }
                
                // Add sample data row
                val sampleRow = sheet.createRow(1)
                val sampleData = arrayOf(
                    "John Doe",
                    "123456789",
                    "123-456-7890",
                    "john.doe@email.com",
                    "123 Main St, City",
                    "2024-01-15",
                    "1000.00",
                    "500.00",
                    "partial"
                )
                
                sampleData.forEachIndexed { index, data ->
                    sampleRow.createCell(index).setCellValue(data)
                }
                
                // Auto-size columns
                for (i in headers.indices) {
                    sheet.autoSizeColumn(i)
                }
                
                // Save using scoped storage
                saveWorkbookToDownloads(context, workbook, fileName)
                workbook.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        
        fun exportInvoicesToExcel(
            context: Context,
            invoices: List<Invoice>,
            customers: List<Customer>,
            fileName: String
        ): Boolean {
            return try {
                val workbook = XSSFWorkbook()
                val sheet = workbook.createSheet("Invoices")
                
                // Create header row
                val headerRow = sheet.createRow(0)
                val headers = arrayOf(
                    "Invoice ID",
                    "Customer Name",
                    "Customer PIB",
                    "Customer Phone",
                    "Customer Email",
                    "Customer Address",
                    "Customer Total Debt",
                    "Invoice Date",
                    "Total Amount",
                    "Paid Amount",
                    "Remaining Amount",
                    "Status",
                    "Payment Status"
                )
                
                headers.forEachIndexed { index, header ->
                    val cell = headerRow.createCell(index)
                    cell.setCellValue(header)
                    
                    // Style header cells
                    val cellStyle = workbook.createCellStyle()
                    val font = workbook.createFont()
                    font.bold = true
                    cellStyle.setFont(font)
                    cell.setCellStyle(cellStyle)
                }
                
                // Add invoice data
                invoices.forEachIndexed { index, invoice ->
                    val row = sheet.createRow(index + 1)
                    val customer = customers.find { it.customerId == invoice.customerId }
                    val remainingAmount = invoice.totalAmount - invoice.paidAmount
                    val paymentStatus = when {
                        invoice.paidAmount >= invoice.totalAmount -> "Fully Paid"
                        invoice.paidAmount > 0 -> "Partially Paid"
                        else -> "Unpaid"
                    }
                    
                    row.createCell(0).setCellValue(invoice.invoiceId.toDouble())
                    row.createCell(1).setCellValue(customer?.name ?: "Unknown")
                    row.createCell(2).setCellValue(customer?.pib ?: "")
                    row.createCell(3).setCellValue(customer?.phone ?: "")
                    row.createCell(4).setCellValue(customer?.email ?: "")
                    row.createCell(5).setCellValue(customer?.address ?: "")
                    row.createCell(6).setCellValue(customer?.totalDebt ?: 0.0)
                    row.createCell(7).setCellValue(dateFormat.format(Date(invoice.date)))
                    row.createCell(8).setCellValue(invoice.totalAmount)
                    row.createCell(9).setCellValue(invoice.paidAmount)
                    row.createCell(10).setCellValue(remainingAmount)
                    row.createCell(11).setCellValue(invoice.status)
                    row.createCell(12).setCellValue(paymentStatus)
                }
                
                // Auto-size columns
                for (i in headers.indices) {
                    sheet.autoSizeColumn(i)
                }
                
                // Save using scoped storage
                saveWorkbookToDownloads(context, workbook, fileName)
                workbook.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        
        fun parseInvoicesFromExcel(
            context: Context,
            uri: Uri,
            existingCustomers: List<Customer>
        ): Pair<List<Invoice>, List<Customer>> {
            val invoices = mutableListOf<Invoice>()
            val newCustomers = mutableListOf<Customer>()
            val errors = mutableListOf<String>()
            
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val workbook = WorkbookFactory.create(inputStream)
                    val sheet = workbook.getSheetAt(0)
                    
                    // Validate header row
                    val headerRow = sheet.getRow(0)
                    if (headerRow == null) {
                        throw IllegalArgumentException("Excel file must contain a header row")
                    }
                    
                    val expectedHeaders = listOf(
                        "Customer Name", "Customer PIB", "Customer Phone", 
                        "Customer Email", "Customer Address", "Invoice Date", 
                        "Total Amount", "Paid Amount", "Payment Status"
                    )
                    
                    // Validate header columns
                    for (i in expectedHeaders.indices) {
                        val headerCell = headerRow.getCell(i)
                        val actualHeader = getCellValueAsString(headerCell)
                        if (!actualHeader.equals(expectedHeaders[i], ignoreCase = true)) {
                            errors.add("Column ${i + 1}: Expected '${expectedHeaders[i]}', found '$actualHeader'")
                        }
                    }
                    
                    if (errors.isNotEmpty()) {
                        throw IllegalArgumentException("Header validation failed: ${errors.joinToString("; ")}")
                    }
                    
                    // Process data rows
                    var processedRows = 0
                    var skippedRows = 0
                    
                    for (rowIndex in 1..sheet.lastRowNum) {
                        val row = sheet.getRow(rowIndex) ?: continue
                        if (isRowEmpty(row)) continue
                        
                        try {
                            val customerName = getCellValueAsString(row.getCell(0)).trim()
                            val customerPib = getCellValueAsString(row.getCell(1)).trim()
                            val customerPhone = getCellValueAsString(row.getCell(2)).trim()
                            val customerEmail = getCellValueAsString(row.getCell(3)).trim()
                            val customerAddress = getCellValueAsString(row.getCell(4)).trim()
                            val dateStr = getCellValueAsString(row.getCell(5)).trim()
                            val totalAmount = getCellValueAsDouble(row.getCell(6))
                            val paidAmount = getCellValueAsDouble(row.getCell(7))
                            val status = getCellValueAsString(row.getCell(8)).trim()
                            
                            // Validate required fields
                            if (customerName.isBlank()) {
                                errors.add("Row ${rowIndex + 1}: Customer name is required")
                                skippedRows++
                                continue
                            }
                            
                            if (dateStr.isBlank()) {
                                errors.add("Row ${rowIndex + 1}: Invoice date is required")
                                skippedRows++
                                continue
                            }
                            
                            if (totalAmount <= 0) {
                                errors.add("Row ${rowIndex + 1}: Total amount must be greater than 0")
                                skippedRows++
                                continue
                            }
                            
                            if (paidAmount < 0) {
                                errors.add("Row ${rowIndex + 1}: Paid amount cannot be negative")
                                skippedRows++
                                continue
                            }
                            
                            if (paidAmount > totalAmount) {
                                errors.add("Row ${rowIndex + 1}: Paid amount cannot exceed total amount")
                                skippedRows++
                                continue
                            }
                            
                            // Validate email format if provided
                            if (customerEmail.isNotBlank() && !isValidEmail(customerEmail)) {
                                errors.add("Row ${rowIndex + 1}: Invalid email format: $customerEmail")
                                skippedRows++
                                continue
                            }
                            
                            // Find or create customer
                            var customer = existingCustomers.find { it.name.equals(customerName, ignoreCase = true) }
                            if (customer == null) {
                                customer = newCustomers.find { it.name.equals(customerName, ignoreCase = true) }
                                if (customer == null) {
                                    customer = Customer(
                                        customerId = 0, // Will be assigned by database
                                        name = customerName,
                                        pib = if (customerPib.isBlank()) null else customerPib,
                                        phone = if (customerPhone.isBlank()) null else customerPhone,
                                        email = if (customerEmail.isBlank()) null else customerEmail,
                                        address = if (customerAddress.isBlank()) null else customerAddress,
                                        totalDebt = 0.0
                                    )
                                    newCustomers.add(customer)
                                }
                            }
                            
                            // Parse and validate date
                            val date = try {
                                val parsedDate = dateFormat.parse(dateStr)
                                if (parsedDate == null) {
                                    errors.add("Row ${rowIndex + 1}: Invalid date format: $dateStr (expected: yyyy-MM-dd)")
                                    skippedRows++
                                    continue
                                }
                                parsedDate.time
                            } catch (e: Exception) {
                                errors.add("Row ${rowIndex + 1}: Invalid date format: $dateStr (expected: yyyy-MM-dd)")
                                skippedRows++
                                continue
                            }
                            
                            // Validate and normalize status
                            val normalizedStatus = when (status.lowercase()) {
                                "pending", "unpaid", "" -> "pending"
                                "partial", "partially paid" -> "partial"
                                "paid", "completed", "complete" -> "paid"
                                else -> {
                                    errors.add("Row ${rowIndex + 1}: Invalid status '$status' (expected: pending, partial, or paid)")
                                    "pending" // Default to pending for invalid status
                                }
                            }
                            
                            val invoice = Invoice(
                                invoiceId = 0, // Will be assigned by database
                                customerId = customer.customerId,
                                date = date,
                                totalAmount = totalAmount,
                                paidAmount = paidAmount,
                                status = normalizedStatus
                            )
                            
                            invoices.add(invoice)
                            processedRows++
                            
                        } catch (e: Exception) {
                            errors.add("Row ${rowIndex + 1}: Unexpected error - ${e.message}")
                            skippedRows++
                        }
                    }
                    
                    workbook.close()
                    
                    // Log import summary
                    if (errors.isNotEmpty()) {
                        val errorSummary = "Import completed with warnings: $processedRows rows processed, $skippedRows rows skipped. Errors: ${errors.take(5).joinToString("; ")}${if (errors.size > 5) "... and ${errors.size - 5} more" else ""}"
                        throw IllegalArgumentException(errorSummary)
                    }
                }
            } catch (e: Exception) {
                if (e is IllegalArgumentException) {
                    throw e // Re-throw validation errors with detailed messages
                } else {
                    throw IllegalArgumentException("Failed to parse Excel file: ${e.message}")
                }
            }
            
            return Pair(invoices, newCustomers)
        }
        
        // Import all data from comprehensive Excel file
        fun importAllDataFromExcel(
            context: Context,
            uri: Uri,
            existingCustomers: List<Customer>,
            existingInvoices: List<Invoice>
        ): ImportResult {
            val result = ImportResult()
            
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val workbook = WorkbookFactory.create(inputStream)
                    
                    // Import from different sheets
                    for (sheetIndex in 0 until workbook.numberOfSheets) {
                        val sheet = workbook.getSheetAt(sheetIndex)
                        val sheetName = sheet.sheetName.lowercase()
                        
                        when {
                            sheetName.contains("invoice") -> {
                                val (invoices, customers) = parseInvoicesFromSheet(sheet, existingCustomers)
                                result.invoices.addAll(invoices)
                                result.customers.addAll(customers)
                            }
                            sheetName.contains("payment") -> {
                                result.payments.addAll(parsePaymentsFromSheet(sheet, existingInvoices))
                            }
                            sheetName.contains("expense") -> {
                                result.expenses.addAll(parseExpensesFromSheet(sheet))
                            }
                            sheetName.contains("stock") -> {
                                result.stockItems.addAll(parseStockItemsFromSheet(sheet))
                            }
                            sheetName.contains("customer") -> {
                                result.customers.addAll(parseCustomersFromSheet(sheet))
                            }
                            // Try to parse monthly sheets
                            else -> {
                                val monthlyData = parseMonthlyDataSheet(sheet, existingCustomers, existingInvoices)
                                result.invoices.addAll(monthlyData.invoices)
                                result.payments.addAll(monthlyData.payments)
                                result.expenses.addAll(monthlyData.expenses)
                                result.customers.addAll(monthlyData.customers)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result.error = e.message
            }
            
            return result
        }
        
        // Parse invoices from sheet
        private fun parseInvoicesFromSheet(sheet: Sheet, existingCustomers: List<Customer>): Pair<List<Invoice>, List<Customer>> {
            val invoices = mutableListOf<Invoice>()
            val newCustomers = mutableListOf<Customer>()
            
            // Find header row and data start
            var headerRowIndex = -1
            for (i in 0..5) {
                val row = sheet.getRow(i) ?: continue
                val firstCell = getCellValueAsString(row.getCell(0)).lowercase()
                if (firstCell.contains("invoice") || firstCell.contains("customer")) {
                    headerRowIndex = i
                    break
                }
            }
            
            if (headerRowIndex == -1) return Pair(invoices, newCustomers)
            
            // Parse data rows
            for (rowIndex in (headerRowIndex + 1)..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex) ?: continue
                if (getCellValueAsString(row.getCell(0)).isBlank()) continue
                
                try {
                    val customerName = getCellValueAsString(row.getCell(1))
                    val dateStr = getCellValueAsString(row.getCell(2))
                    val totalAmount = getCellValueAsDouble(row.getCell(3))
                    val paidAmount = getCellValueAsDouble(row.getCell(4))
                    val status = getCellValueAsString(row.getCell(5))
                    
                    if (customerName.isBlank()) continue
                    
                    // Find or create customer
                    var customer = existingCustomers.find { it.name == customerName }
                    if (customer == null) {
                        customer = newCustomers.find { it.name == customerName }
                        if (customer == null) {
                            customer = Customer(
                                name = customerName,
                                phone = "",
                                address = "",
                                pib = null,
                                email = null
                            )
                            newCustomers.add(customer)
                        }
                    }
                    
                    // Parse date
                    val date = try {
                        dateFormat.parse(dateStr)?.time ?: System.currentTimeMillis()
                    } catch (e: Exception) {
                        System.currentTimeMillis()
                    }
                    
                    val invoice = Invoice(
                        customerId = customer.customerId,
                        date = date,
                        totalAmount = totalAmount,
                        paidAmount = paidAmount,
                        status = status.ifBlank { "pending" }
                    )
                    
                    invoices.add(invoice)
                } catch (e: Exception) {
                    // Skip invalid rows
                    continue
                }
            }
            
            return Pair(invoices, newCustomers)
        }
        
        // Parse payments from sheet
        private fun parsePaymentsFromSheet(sheet: Sheet, @Suppress("UNUSED_PARAMETER") existingInvoices: List<Invoice>): List<Payment> {
            val payments = mutableListOf<Payment>()
            
            // Find header row
            var headerRowIndex = -1
            for (i in 0..5) {
                val row = sheet.getRow(i) ?: continue
                val firstCell = getCellValueAsString(row.getCell(0)).lowercase()
                if (firstCell.contains("payment")) {
                    headerRowIndex = i
                    break
                }
            }
            
            if (headerRowIndex == -1) return payments
            
            // Parse data rows
            for (rowIndex in (headerRowIndex + 1)..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex) ?: continue
                if (getCellValueAsString(row.getCell(0)).isBlank()) continue
                
                try {
                    val invoiceId = getCellValueAsDouble(row.getCell(1)).toInt()
                    val amount = getCellValueAsDouble(row.getCell(3))
                    val dateStr = getCellValueAsString(row.getCell(4))
                    
                    // Parse date
                    val date = try {
                        dateFormat.parse(dateStr)?.time ?: System.currentTimeMillis()
                    } catch (e: Exception) {
                        System.currentTimeMillis()
                    }
                    
                    val payment = Payment(
                        invoiceId = invoiceId,
                        amount = amount,
                        date = date
                    )
                    
                    payments.add(payment)
                } catch (e: Exception) {
                    continue
                }
            }
            
            return payments
        }
        
        // Parse expenses from sheet
        private fun parseExpensesFromSheet(sheet: Sheet): List<Expense> {
            val expenses = mutableListOf<Expense>()
            
            // Find header row
            var headerRowIndex = -1
            for (i in 0..5) {
                val row = sheet.getRow(i) ?: continue
                val firstCell = getCellValueAsString(row.getCell(0)).lowercase()
                if (firstCell.contains("expense")) {
                    headerRowIndex = i
                    break
                }
            }
            
            if (headerRowIndex == -1) return expenses
            
            // Parse data rows
            for (rowIndex in (headerRowIndex + 1)..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex) ?: continue
                if (getCellValueAsString(row.getCell(0)).isBlank()) continue
                
                try {
                    val category = getCellValueAsString(row.getCell(1))
                    val amount = getCellValueAsDouble(row.getCell(2))
                    val dateStr = getCellValueAsString(row.getCell(3))
                    val notes = getCellValueAsString(row.getCell(4))
                    
                    // Parse date
                    val date = try {
                        dateFormat.parse(dateStr)?.time ?: System.currentTimeMillis()
                    } catch (e: Exception) {
                        System.currentTimeMillis()
                    }
                    
                    val expense = Expense(
                        category = category,
                        amount = amount,
                        date = date,
                        notes = notes.ifBlank { null }
                    )
                    
                    expenses.add(expense)
                } catch (e: Exception) {
                    continue
                }
            }
            
            return expenses
        }
        
        // Parse stock items from sheet
        private fun parseStockItemsFromSheet(sheet: Sheet): List<StockItem> {
            val stockItems = mutableListOf<StockItem>()
            
            // Find header row
            var headerRowIndex = -1
            for (i in 0..5) {
                val row = sheet.getRow(i) ?: continue
                val firstCell = getCellValueAsString(row.getCell(0)).lowercase()
                if (firstCell.contains("stock") || firstCell.contains("name")) {
                    headerRowIndex = i
                    break
                }
            }
            
            if (headerRowIndex == -1) return stockItems
            
            // Parse data rows
            for (rowIndex in (headerRowIndex + 1)..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex) ?: continue
                if (getCellValueAsString(row.getCell(1)).isBlank()) continue
                
                try {
                    val name = getCellValueAsString(row.getCell(1))
                    val purchasePrice = getCellValueAsDouble(row.getCell(2))
                    val sellingPrice = getCellValueAsDouble(row.getCell(3))
                    val quantity = getCellValueAsDouble(row.getCell(4)).toInt()
                    
                    val stockItem = StockItem(
                        name = name,
                        purchasePrice = purchasePrice,
                        sellingPrice = sellingPrice,
                        quantity = quantity
                    )
                    
                    stockItems.add(stockItem)
                } catch (e: Exception) {
                    continue
                }
            }
            
            return stockItems
        }
        
        // Parse customers from sheet
        private fun parseCustomersFromSheet(sheet: Sheet): List<Customer> {
            val customers = mutableListOf<Customer>()
            
            // Find header row
            var headerRowIndex = -1
            for (i in 0..5) {
                val row = sheet.getRow(i) ?: continue
                val firstCell = getCellValueAsString(row.getCell(0)).lowercase()
                if (firstCell.contains("customer") || firstCell.contains("name")) {
                    headerRowIndex = i
                    break
                }
            }
            
            if (headerRowIndex == -1) return customers
            
            // Parse data rows
            for (rowIndex in (headerRowIndex + 1)..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex) ?: continue
                if (getCellValueAsString(row.getCell(1)).isBlank()) continue
                
                try {
                    val name = getCellValueAsString(row.getCell(1))
                    val pib = getCellValueAsString(row.getCell(2)).ifBlank { null }
                    val phone = getCellValueAsString(row.getCell(3))
                    val email = getCellValueAsString(row.getCell(4)).ifBlank { null }
                    val address = getCellValueAsString(row.getCell(5))
                    val totalDebt = getCellValueAsDouble(row.getCell(6))
                    
                    val customer = Customer(
                        name = name,
                        pib = pib,
                        phone = phone,
                        email = email,
                        address = address,
                        totalDebt = totalDebt
                    )
                    
                    customers.add(customer)
                } catch (e: Exception) {
                    continue
                }
            }
            
            return customers
        }
        
        // Parse monthly data sheet
        private fun parseMonthlyDataSheet(
            sheet: Sheet,
            existingCustomers: List<Customer>,
            existingInvoices: List<Invoice>
        ): ImportResult {
            val result = ImportResult()
            
            var currentRow = 0
            while (currentRow <= sheet.lastRowNum) {
                val row = sheet.getRow(currentRow)
                if (row == null) {
                    currentRow++
                    continue
                }
                val cellValue = getCellValueAsString(row.getCell(0)).lowercase()
                
                when {
                    cellValue.contains("invoice") -> {
                        val (invoices, customers) = parseInvoicesFromSheet(sheet, existingCustomers)
                        result.invoices.addAll(invoices)
                        result.customers.addAll(customers)
                        return result
                    }
                    cellValue.contains("payment") -> {
                        result.payments.addAll(parsePaymentsFromSheet(sheet, existingInvoices))
                        return result
                    }
                    cellValue.contains("expense") -> {
                        result.expenses.addAll(parseExpensesFromSheet(sheet))
                        return result
                    }
                }
                currentRow++
            }
            
            return result
        }
        
        // Data class for import results
        data class ImportResult(
            val invoices: MutableList<Invoice> = mutableListOf(),
            val payments: MutableList<Payment> = mutableListOf(),
            val expenses: MutableList<Expense> = mutableListOf(),
            val stockItems: MutableList<StockItem> = mutableListOf(),
            val customers: MutableList<Customer> = mutableListOf(),
            var error: String? = null
        )
        
        private fun getCellValueAsString(cell: Cell?): String {
            return when (cell?.cellType) {
                CellType.STRING -> cell.stringCellValue
                CellType.NUMERIC -> cell.numericCellValue.toString()
                CellType.BOOLEAN -> cell.booleanCellValue.toString()
                else -> ""
            }
        }

        private fun getCellValueAsDouble(cell: Cell?): Double {
            return when (cell?.cellType) {
                CellType.NUMERIC -> cell.numericCellValue
                CellType.STRING -> {
                    try {
                        cell.stringCellValue.toDouble()
                    } catch (e: NumberFormatException) {
                        0.0
                    }
                }
                else -> 0.0
            }
        }
        
        private fun isRowEmpty(row: Row): Boolean {
            for (cellIndex in 0 until 9) { // Check first 9 columns
                val cell = row.getCell(cellIndex)
                if (cell != null && getCellValueAsString(cell).trim().isNotBlank()) {
                    return false
                }
            }
            return true
        }
        
        private fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        
        private fun saveWorkbookToDownloads(context: Context, workbook: XSSFWorkbook, fileName: String): Boolean {
            return try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Use MediaStore for Android 10+ (API 29+)
                    saveWithMediaStore(context, workbook, fileName)
                } else {
                    // Use legacy external storage for older Android versions
                    saveWithLegacyStorage(context, workbook, fileName)
                }
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to save workbook: ${e.message}", e)
                // Try fallback method
                try {
                    saveWithInternalStorage(context, workbook, fileName)
                } catch (fallbackException: Exception) {
                    android.util.Log.e("ExcelUtils", "Fallback save also failed: ${fallbackException.message}", fallbackException)
                    false
                }
            }
        }
        
        private fun saveWithMediaStore(context: Context, workbook: XSSFWorkbook, fileName: String): Boolean {
            return try {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.MediaColumns.IS_PENDING, 1)
                    }
                }
                
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                
                uri?.let { fileUri ->
                    resolver.openOutputStream(fileUri)?.use { outputStream ->
                        workbook.write(outputStream)
                        outputStream.flush()
                    }
                    
                    // Mark file as not pending
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        contentValues.clear()
                        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                        resolver.update(fileUri, contentValues, null, null)
                    }
                    
                    android.util.Log.d("ExcelUtils", "File saved successfully via MediaStore: $fileName")
                    true
                } ?: run {
                    android.util.Log.e("ExcelUtils", "Failed to create MediaStore entry for: $fileName")
                    false
                }
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "MediaStore save failed: ${e.message}", e)
                throw e
            }
        }
        
        private fun saveWithLegacyStorage(context: Context, workbook: XSSFWorkbook, fileName: String): Boolean {
            return try {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) {
                    downloadsDir.mkdirs()
                }
                
                val file = java.io.File(downloadsDir, fileName)
                
                // Check if we can write to the directory
                if (!downloadsDir.canWrite()) {
                    android.util.Log.e("ExcelUtils", "Cannot write to downloads directory")
                    throw SecurityException("Cannot write to downloads directory")
                }
                
                FileOutputStream(file).use { outputStream ->
                    workbook.write(outputStream)
                    outputStream.flush()
                }
                
                android.util.Log.d("ExcelUtils", "File saved successfully via legacy storage: ${file.absolutePath}")
                true
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Legacy storage save failed: ${e.message}", e)
                throw e
            }
        }
        
        private fun saveWithInternalStorage(context: Context, workbook: XSSFWorkbook, fileName: String): Boolean {
            return try {
                // Save to app's internal storage as fallback
                val file = java.io.File(context.filesDir, fileName)
                
                FileOutputStream(file).use { outputStream ->
                    workbook.write(outputStream)
                    outputStream.flush()
                }
                
                android.util.Log.d("ExcelUtils", "File saved to internal storage as fallback: ${file.absolutePath}")
                true
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Internal storage save failed: ${e.message}", e)
                false
            }
        }
        
        // Export all data to an OutputStream (for use with URI)
        // Memory-efficient export for large datasets
        private fun exportLargeDatasetToStream(
            outputStream: OutputStream,
            invoices: List<Invoice>,
            payments: List<Payment>,
            expenses: List<Expense>,
            stockItems: List<StockItem>,
            customers: List<Customer>
        ): Boolean {
            return try {
                android.util.Log.d("ExcelUtils", "Using large dataset export approach")
                val workbook = XSSFWorkbook()
                
                try {
                    // For large datasets, only export recent data to prevent memory issues
                    val maxRecordsPerType = 1000
                    
                    // Get recent data only
                    val recentInvoices = invoices.takeLast(maxRecordsPerType)
                    val recentPayments = payments.takeLast(maxRecordsPerType)
                    val recentExpenses = expenses.takeLast(maxRecordsPerType)
                    val recentStockItems = stockItems.takeLast(maxRecordsPerType)
                    val recentCustomers = customers.takeLast(maxRecordsPerType)
                    
                    android.util.Log.d("ExcelUtils", "Exporting recent data: ${recentInvoices.size} invoices, ${recentPayments.size} payments, ${recentExpenses.size} expenses")
                    
                    // Create a single summary sheet with recent data
                    createSummarySheet(workbook, recentInvoices, recentPayments, recentExpenses)
                    
                    // Create individual sheets for each data type
                    createStockItemsSheet(workbook, recentStockItems)
                    createCustomersSheet(workbook, recentCustomers)
                    
                    // Add a note about data limitation
                    val noteSheet = workbook.createSheet("Export Info")
                    val noteRow = noteSheet.createRow(0)
                    noteRow.createCell(0).setCellValue("Note: Due to large dataset size, only the most recent ${maxRecordsPerType} records of each type are included.")
                    val noteRow2 = noteSheet.createRow(1)
                    noteRow2.createCell(0).setCellValue("Total records in database: ${invoices.size + payments.size + expenses.size + stockItems.size + customers.size}")
                    val noteRow3 = noteSheet.createRow(2)
                    noteRow3.createCell(0).setCellValue("Export date: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}")
                    
                    // Write to output stream
                    workbook.write(outputStream)
                    outputStream.flush()
                    
                    true
                } finally {
                    workbook.close()
                }
            } catch (e: OutOfMemoryError) {
                android.util.Log.e("ExcelUtils", "Out of memory during large dataset export: ${e.message}", e)
                System.gc()
                false
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to export large dataset: ${e.message}", e)
                false
            }
        }

        fun exportAllDataToStream(
            outputStream: OutputStream,
            invoices: List<Invoice>,
            payments: List<Payment>,
            expenses: List<Expense>,
            stockItems: List<StockItem>,
            customers: List<Customer>
        ): Boolean {
            return try {
                // Check data size and use memory-efficient approach for large datasets
                val totalRecords = invoices.size + payments.size + expenses.size + stockItems.size + customers.size
                android.util.Log.d("ExcelUtils", "Exporting $totalRecords total records")
                
                if (totalRecords > 5000) {
                    // Use streaming approach for large datasets
                    return exportLargeDatasetToStream(outputStream, invoices, payments, expenses, stockItems, customers)
                }
                
                val workbook = XSSFWorkbook()
                
                try {
                    // Create header style
                    val headerStyle = workbook.createCellStyle()
                    val headerFont = workbook.createFont()
                    headerFont.bold = true
                    headerStyle.setFont(headerFont)
                    
                    // Create separate sheets for each data type with clear headers for import
                    createSimpleCustomersSheet(workbook, customers, headerStyle)
                    createSimpleInvoicesSheet(workbook, invoices, customers, headerStyle)
                    createSimplePaymentsSheet(workbook, payments, invoices, customers, headerStyle)
                    createSimpleExpensesSheet(workbook, expenses, headerStyle)
                    createSimpleStockItemsSheet(workbook, stockItems, headerStyle)
                    
                    // Write to output stream
                    workbook.write(outputStream)
                    outputStream.flush()
                    
                    true
                } finally {
                    workbook.close()
                }
            } catch (e: OutOfMemoryError) {
                android.util.Log.e("ExcelUtils", "Out of memory during export: ${e.message}", e)
                System.gc() // Force garbage collection
                false
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to export to stream: ${e.message}", e)
                false
            }
        }
        
        // Create simple sheets for import/export with clear headers
        private fun createSimpleCustomersSheet(workbook: XSSFWorkbook, customers: List<Customer>, headerStyle: CellStyle) {
            val sheet = workbook.createSheet("Customers")
            
            // Headers
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("Customer ID", "Name", "PIB", "Phone", "Email", "Address", "Total Debt")
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.setCellStyle(headerStyle)
            }
            
            // Data rows
            customers.forEachIndexed { index, customer ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(customer.customerId.toDouble())
                row.createCell(1).setCellValue(customer.name)
                row.createCell(2).setCellValue(customer.pib ?: "")
                row.createCell(3).setCellValue(customer.phone)
                row.createCell(4).setCellValue(customer.email ?: "")
                row.createCell(5).setCellValue(customer.address)
                row.createCell(6).setCellValue(customer.totalDebt)
            }
            
            // Auto-size columns
            for (i in 0..6) {
                sheet.autoSizeColumn(i)
            }
        }
        
        private fun createSimpleInvoicesSheet(workbook: XSSFWorkbook, invoices: List<Invoice>, customers: List<Customer>, headerStyle: CellStyle) {
            val sheet = workbook.createSheet("Invoices")
            
            // Headers
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("Invoice ID", "Customer ID", "Customer Name", "Date", "Total Amount", "Paid Amount", "Status")
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.setCellStyle(headerStyle)
            }
            
            // Data rows
            invoices.forEachIndexed { index, invoice ->
                val row = sheet.createRow(index + 1)
                val customer = customers.find { it.customerId == invoice.customerId }
                
                row.createCell(0).setCellValue(invoice.invoiceId.toDouble())
                row.createCell(1).setCellValue(invoice.customerId.toDouble())
                row.createCell(2).setCellValue(customer?.name ?: "Unknown")
                row.createCell(3).setCellValue(dateFormat.format(Date(invoice.date)))
                row.createCell(4).setCellValue(invoice.totalAmount)
                row.createCell(5).setCellValue(invoice.paidAmount)
                row.createCell(6).setCellValue(invoice.status)
            }
            
            // Auto-size columns
            for (i in 0..6) {
                sheet.autoSizeColumn(i)
            }
        }
        
        private fun createSimplePaymentsSheet(workbook: XSSFWorkbook, payments: List<Payment>, invoices: List<Invoice>, customers: List<Customer>, headerStyle: CellStyle) {
            val sheet = workbook.createSheet("Payments")
            
            // Headers
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("Payment ID", "Invoice ID", "Customer Name", "Amount", "Date")
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.setCellStyle(headerStyle)
            }
            
            // Data rows
            payments.forEachIndexed { index, payment ->
                val row = sheet.createRow(index + 1)
                val invoice = invoices.find { it.invoiceId == payment.invoiceId }
                val customer = invoice?.let { customers.find { c -> c.customerId == it.customerId } }
                
                row.createCell(0).setCellValue(payment.paymentId.toDouble())
                row.createCell(1).setCellValue(payment.invoiceId.toDouble())
                row.createCell(2).setCellValue(customer?.name ?: "Unknown")
                row.createCell(3).setCellValue(payment.amount)
                row.createCell(4).setCellValue(dateFormat.format(Date(payment.date)))
            }
            
            // Auto-size columns
            for (i in 0..4) {
                sheet.autoSizeColumn(i)
            }
        }
        
        private fun createSimpleExpensesSheet(workbook: XSSFWorkbook, expenses: List<Expense>, headerStyle: CellStyle) {
            val sheet = workbook.createSheet("Expenses")
            
            // Headers
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("Expense ID", "Category", "Amount", "Date", "Notes")
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.setCellStyle(headerStyle)
            }
            
            // Data rows
            expenses.forEachIndexed { index, expense ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(expense.expenseId.toDouble())
                row.createCell(1).setCellValue(expense.category)
                row.createCell(2).setCellValue(expense.amount)
                row.createCell(3).setCellValue(dateFormat.format(Date(expense.date)))
                row.createCell(4).setCellValue(expense.notes ?: "")
            }
            
            // Auto-size columns
            for (i in 0..4) {
                sheet.autoSizeColumn(i)
            }
        }
        
        private fun createSimpleStockItemsSheet(workbook: XSSFWorkbook, stockItems: List<StockItem>, headerStyle: CellStyle) {
            val sheet = workbook.createSheet("Stock Items")
            
            // Headers
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("Stock ID", "Name", "Purchase Price", "Selling Price", "Quantity", "Total Value")
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.setCellStyle(headerStyle)
            }
            
            // Data rows
            stockItems.forEachIndexed { index, item ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(item.stockId.toDouble())
                row.createCell(1).setCellValue(item.name)
                row.createCell(2).setCellValue(item.purchasePrice)
                row.createCell(3).setCellValue(item.sellingPrice)
                row.createCell(4).setCellValue(item.quantity.toDouble())
                row.createCell(5).setCellValue(item.sellingPrice * item.quantity)
            }
            
            // Auto-size columns
            for (i in 0..5) {
                sheet.autoSizeColumn(i)
            }
        }
        
        // Export sample template to an OutputStream
        fun exportSampleTemplateToStream(outputStream: OutputStream): Boolean {
            return try {
                val workbook = XSSFWorkbook()
                
                // Create header style
                val headerStyle = workbook.createCellStyle()
                val headerFont = workbook.createFont()
                headerFont.bold = true
                headerStyle.setFont(headerFont)
                
                // Create Customers sheet
                val customersSheet = workbook.createSheet("Customers")
                val customerHeaders = arrayOf("Customer ID", "Name", "PIB", "Phone", "Email", "Address", "Total Debt")
                val customerHeaderRow = customersSheet.createRow(0)
                customerHeaders.forEachIndexed { index, header ->
                    val cell = customerHeaderRow.createCell(index)
                    cell.setCellValue(header)
                    cell.setCellStyle(headerStyle)
                }
                
                // Add sample customer data
                val sampleCustomerRow = customersSheet.createRow(1)
                val sampleCustomerData = arrayOf("1", "John Doe", "123456789", "123-456-7890", "john.doe@email.com", "123 Main St, City", "0.00")
                sampleCustomerData.forEachIndexed { index, data ->
                    sampleCustomerRow.createCell(index).setCellValue(data)
                }
                
                // Create Invoices sheet
                val invoicesSheet = workbook.createSheet("Invoices")
                val invoiceHeaders = arrayOf("Invoice ID", "Customer ID", "Date (YYYY-MM-DD)", "Total Amount", "Paid Amount", "Status")
                val invoiceHeaderRow = invoicesSheet.createRow(0)
                invoiceHeaders.forEachIndexed { index, header ->
                    val cell = invoiceHeaderRow.createCell(index)
                    cell.setCellValue(header)
                    cell.setCellStyle(headerStyle)
                }
                
                // Add sample invoice data
                val sampleInvoiceRow = invoicesSheet.createRow(1)
                val sampleInvoiceData = arrayOf("1", "1", "2024-01-15", "1000.00", "500.00", "partial")
                sampleInvoiceData.forEachIndexed { index, data ->
                    sampleInvoiceRow.createCell(index).setCellValue(data)
                }
                
                // Create Payments sheet
                val paymentsSheet = workbook.createSheet("Payments")
                val paymentHeaders = arrayOf("Payment ID", "Invoice ID", "Amount", "Date (YYYY-MM-DD)")
                val paymentHeaderRow = paymentsSheet.createRow(0)
                paymentHeaders.forEachIndexed { index, header ->
                    val cell = paymentHeaderRow.createCell(index)
                    cell.setCellValue(header)
                    cell.setCellStyle(headerStyle)
                }
                
                // Add sample payment data
                val samplePaymentRow = paymentsSheet.createRow(1)
                val samplePaymentData = arrayOf("1", "1", "500.00", "2024-01-15")
                samplePaymentData.forEachIndexed { index, data ->
                    samplePaymentRow.createCell(index).setCellValue(data)
                }
                
                // Create Expenses sheet
                val expensesSheet = workbook.createSheet("Expenses")
                val expenseHeaders = arrayOf("Expense ID", "Category", "Amount", "Date (YYYY-MM-DD)", "Notes")
                val expenseHeaderRow = expensesSheet.createRow(0)
                expenseHeaders.forEachIndexed { index, header ->
                    val cell = expenseHeaderRow.createCell(index)
                    cell.setCellValue(header)
                    cell.setCellStyle(headerStyle)
                }
                
                // Add sample expense data
                val sampleExpenseRow = expensesSheet.createRow(1)
                val sampleExpenseData = arrayOf("1", "Office Supplies", "150.00", "2024-01-15", "Printer paper and ink")
                sampleExpenseData.forEachIndexed { index, data ->
                    sampleExpenseRow.createCell(index).setCellValue(data)
                }
                
                // Create Stock Items sheet
                val stockSheet = workbook.createSheet("Stock Items")
                val stockHeaders = arrayOf("Stock ID", "Name", "Purchase Price", "Selling Price", "Quantity")
                val stockHeaderRow = stockSheet.createRow(0)
                stockHeaders.forEachIndexed { index, header ->
                    val cell = stockHeaderRow.createCell(index)
                    cell.setCellValue(header)
                    cell.setCellStyle(headerStyle)
                }
                
                // Add sample stock data
                val sampleStockRow = stockSheet.createRow(1)
                val sampleStockData = arrayOf("1", "Product A", "50.00", "75.00", "100")
                sampleStockData.forEachIndexed { index, data ->
                    sampleStockRow.createCell(index).setCellValue(data)
                }
                
                // Auto-size all columns in all sheets
                listOf(customersSheet, invoicesSheet, paymentsSheet, expensesSheet, stockSheet).forEach { sheet ->
                    for (i in 0..6) {
                        sheet.autoSizeColumn(i)
                    }
                }
                
                // Write to output stream
                workbook.write(outputStream)
                outputStream.flush()
                workbook.close()
                true
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to export template to stream: ${e.message}", e)
                false
            }
        }
        
        // Export sample template (legacy method)
        fun exportSampleTemplate(context: Context, fileName: String): Boolean {
            return try {
                val workbook = XSSFWorkbook()
                
                // Create a simple template sheet
                val sheet = workbook.createSheet("Template")
                val headerRow = sheet.createRow(0)
                val headers = arrayOf("Customer Name", "Invoice Date", "Amount", "Status")
                
                headers.forEachIndexed { index, header ->
                    val cell = headerRow.createCell(index)
                    cell.setCellValue(header)
                    
                    val cellStyle = workbook.createCellStyle()
                    val font = workbook.createFont()
                    font.bold = true
                    cellStyle.setFont(font)
                    cell.setCellStyle(cellStyle)
                }
                
                // Auto-size columns
                for (i in headers.indices) {
                    sheet.autoSizeColumn(i)
                }
                
                // Save using scoped storage
                saveWorkbookToDownloads(context, workbook, fileName)
                workbook.close()
                true
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to export template: ${e.message}", e)
                false
            }
        }
        
        // Import data from an InputStream
        fun importDataFromStream(inputStream: InputStream): ImportResult {
            return try {
                val workbook = XSSFWorkbook(inputStream)
                val importedInvoices = mutableListOf<Invoice>()
                val importedPayments = mutableListOf<Payment>()
                val importedExpenses = mutableListOf<Expense>()
                val importedStockItems = mutableListOf<StockItem>()
                val importedCustomers = mutableListOf<Customer>()
                val errors = mutableListOf<String>()
                
                // Import customers
                val customersSheet = workbook.getSheet("Customers")
                if (customersSheet != null) {
                    try {
                        val customerResult = parseCustomersFromSheet(customersSheet)
                        importedCustomers.addAll(customerResult)
                    } catch (e: Exception) {
                        errors.add("Failed to parse customers: ${e.message}")
                    }
                }
                
                // Import invoices
                val invoicesSheet = workbook.getSheet("Invoices")
                if (invoicesSheet != null) {
                    try {
                        val (invoices, newCustomers) = parseInvoicesFromSheet(invoicesSheet, importedCustomers)
                        importedInvoices.addAll(invoices)
                        importedCustomers.addAll(newCustomers)
                    } catch (e: Exception) {
                        errors.add("Failed to parse invoices: ${e.message}")
                    }
                }
                
                // Import payments
                val paymentsSheet = workbook.getSheet("Payments")
                if (paymentsSheet != null) {
                    try {
                        val paymentResult = parsePaymentsFromSheet(paymentsSheet, importedInvoices)
                        importedPayments.addAll(paymentResult)
                    } catch (e: Exception) {
                        errors.add("Failed to parse payments: ${e.message}")
                    }
                }
                
                // Import expenses
                val expensesSheet = workbook.getSheet("Expenses")
                if (expensesSheet != null) {
                    try {
                        val expenseResult = parseExpensesFromSheet(expensesSheet)
                        importedExpenses.addAll(expenseResult)
                    } catch (e: Exception) {
                        errors.add("Failed to parse expenses: ${e.message}")
                    }
                }
                
                // Import stock items
                val stockSheet = workbook.getSheet("Stock Items")
                if (stockSheet != null) {
                    try {
                        val stockResult = parseStockItemsFromSheet(stockSheet)
                        importedStockItems.addAll(stockResult)
                    } catch (e: Exception) {
                        errors.add("Failed to parse stock items: ${e.message}")
                    }
                }
                
                workbook.close()
                
                ImportResult(
                    invoices = importedInvoices,
                    payments = importedPayments,
                    expenses = importedExpenses,
                    stockItems = importedStockItems,
                    customers = importedCustomers,
                    error = if (errors.isNotEmpty()) errors.joinToString("; ") else null
                )
            } catch (e: Exception) {
                android.util.Log.e("ExcelUtils", "Failed to import from stream: ${e.message}", e)
                ImportResult(
                    invoices = mutableListOf(),
                    payments = mutableListOf(),
                    expenses = mutableListOf(),
                    stockItems = mutableListOf(),
                    customers = mutableListOf(),
                    error = "Failed to read Excel file: ${e.message}"
                )
            }
        }
        
        private data class ParseResult<T>(
            val data: List<T>,
            val errors: List<String>
        )
    }
}