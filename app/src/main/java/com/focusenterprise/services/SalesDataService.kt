package com.focusenterprise.services

import com.focusenterprise.data.*
import com.focusenterprise.data.repositories.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

/**
 * Service for aggregating sales data from multiple entities into SalesRecord format
 */
class SalesDataService(
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    private val stockRepository: StockRepository,
    private val invoiceItemRepository: InvoiceItemRepository
) {
    
    /**
     * Aggregates all sales data into SalesRecord format with memory optimization
     */
    suspend fun getAllSalesRecords(maxRecords: Int = 10000): List<SalesRecord> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("SalesDataService", "Starting sales data aggregation")
                
                // Get all required data
                val invoices = invoiceRepository.allInvoices.first()
                val customers = customerRepository.allCustomers.first()
                val stockItems = stockRepository.allStockItems.first()
                val invoiceItems = invoiceItemRepository.allInvoiceItems.first()
                
                Log.d("SalesDataService", "Loaded ${invoices.size} invoices, ${invoiceItems.size} items")
                
                // Create lookup maps for performance
                val customerMap = customers.associateBy { it.customerId }
                val stockMap = stockItems.associateBy { it.stockId }
                val invoiceMap = invoices.associateBy { it.invoiceId }
                
                val salesRecords = mutableListOf<SalesRecord>()
                
                // Process invoice items in chunks to prevent memory issues
                val chunkSize = 1000
                val chunks = invoiceItems.chunked(chunkSize)
                
                for ((chunkIndex, chunk) in chunks.withIndex()) {
                    Log.d("SalesDataService", "Processing chunk ${chunkIndex + 1}/${chunks.size}")
                    
                    chunk.forEach { item ->
                        try {
                            val invoice = invoiceMap[item.invoiceId]
                            val customer = invoice?.let { customerMap[it.customerId] }
                            val stockItem = stockMap[item.stockId]
                            
                            if (invoice != null && customer != null && stockItem != null) {
                                val salesRecord = SalesRecord(
                                    date = invoice.date,
                                    customerName = customer.name,
                                    product = stockItem.name,
                                    quantity = item.quantity,
                                    price = item.unitPrice,
                                    total = item.totalPrice
                                )
                                
                                if (salesRecord.isValid()) {
                                    salesRecords.add(salesRecord)
                                    
                                    // Limit records to prevent memory issues
                                    if (salesRecords.size >= maxRecords) {
                                        Log.w("SalesDataService", "Reached max records limit: $maxRecords")
                                        return@withContext salesRecords
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("SalesDataService", "Error processing item ${item.itemId}", e)
                        }
                    }
                    
                    // Force garbage collection between chunks
                    if (chunkIndex % 5 == 0) {
                        System.gc()
                    }
                }
                
                Log.d("SalesDataService", "Generated ${salesRecords.size} sales records")
                salesRecords.sortedByDescending { it.date } // Most recent first
                
            } catch (e: OutOfMemoryError) {
                Log.e("SalesDataService", "Out of memory during sales data aggregation", e)
                System.gc()
                emptyList()
            } catch (e: Exception) {
                Log.e("SalesDataService", "Error aggregating sales data", e)
                emptyList()
            }
        }
    }
    
    /**
     * Gets sales records for a specific date range
     */
    suspend fun getSalesRecordsByDateRange(
        startDate: Long,
        endDate: Long,
        maxRecords: Int = 5000
    ): List<SalesRecord> {
        return withContext(Dispatchers.IO) {
            try {
                val invoices = invoiceRepository.getInvoicesByDateRange(startDate, endDate).first()
                val customers = customerRepository.allCustomers.first()
                val stockItems = stockRepository.allStockItems.first()
                val invoiceItems = invoiceItemRepository.getItemsByInvoiceIds(invoices.map { it.invoiceId }).first()
                
                // Create lookup maps
                val customerMap = customers.associateBy { it.customerId }
                val stockMap = stockItems.associateBy { it.stockId }
                val invoiceMap = invoices.associateBy { it.invoiceId }
                
                val salesRecords = mutableListOf<SalesRecord>()
                
                for (item in invoiceItems) {
                    try {
                        val invoice = invoiceMap[item.invoiceId]
                        val customer = invoice?.let { customerMap[it.customerId] }
                        val stockItem = stockMap[item.stockId]
                        
                        if (invoice != null && customer != null && stockItem != null) {
                            val salesRecord = SalesRecord(
                                date = invoice.date,
                                customerName = customer.name,
                                product = stockItem.name,
                                quantity = item.quantity,
                                price = item.unitPrice,
                                total = item.totalPrice
                            )
                            
                            if (salesRecord.isValid()) {
                                salesRecords.add(salesRecord)
                                
                                if (salesRecords.size >= maxRecords) {
                                    break
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("SalesDataService", "Error processing item ${item.itemId}", e)
                    }
                }
                
                salesRecords.sortedByDescending { it.date }
                
            } catch (e: Exception) {
                Log.e("SalesDataService", "Error getting sales records by date range", e)
                emptyList()
            }
        }
    }
    
    /**
     * Get sales records with memory optimization for large datasets
     * Processes data in chunks to prevent memory issues
     */
    suspend fun getSalesRecordsChunked(
        startDate: Long, 
        endDate: Long, 
        chunkSize: Int = 1000
    ): List<SalesRecord> = withContext(Dispatchers.IO) {
        
        val allInvoices = invoiceRepository.getInvoicesByDateRange(startDate, endDate).first()
        val allCustomers = customerRepository.allCustomers.first()
        val allStockItems = stockRepository.allStockItems.first()
        
        // Create lookup maps
        val customerMap = allCustomers.associateBy { it.customerId }
        val stockMap = allStockItems.associateBy { it.stockId }
        
        val salesRecords = mutableListOf<SalesRecord>()
        
        // Process invoices in chunks
        allInvoices.chunked(chunkSize).forEach { invoiceChunk ->
            val invoiceIds = invoiceChunk.map { it.invoiceId }
            val invoiceMap = invoiceChunk.associateBy { it.invoiceId }
            
            // Get items for this chunk of invoices
            val chunkItems = invoiceItemRepository.getItemsByInvoiceIds(invoiceIds).first()
            
            // Convert to sales records
            chunkItems.forEach { item ->
                val invoice = invoiceMap[item.invoiceId]
                val customer = invoice?.let { customerMap[it.customerId] }
                val stockItem = stockMap[item.stockId]
                
                if (invoice != null && customer != null && stockItem != null) {
                    salesRecords.add(
                        SalesRecord(
                            date = invoice.date,
                            customerName = customer.name,
                            product = stockItem.name,
                            quantity = item.quantity,
                            price = item.unitPrice,
                            total = item.totalPrice
                        )
                    )
                }
            }
        }
        
        salesRecords
    }
    
    /**
     * Converts SalesRecord back to database entities for import
     */
    suspend fun convertSalesRecordToEntities(
        salesRecord: SalesRecord,
        existingCustomers: List<Customer>,
        existingStockItems: List<StockItem>
    ): Triple<Customer?, StockItem?, InvoiceItem?> {
        return withContext(Dispatchers.IO) {
            try {
                // Find or create customer
                val customer = existingCustomers.find { 
                    it.name.equals(salesRecord.customerName, ignoreCase = true) 
                } ?: Customer(
                    name = salesRecord.customerName,
                    pib = "",
                    phone = "",
                    email = "",
                    address = "",
                    totalDebt = 0.0
                )
                
                // Find or create stock item
                val stockItem = existingStockItems.find {
                    it.name.equals(salesRecord.product, ignoreCase = true)
                } ?: StockItem(
                    name = salesRecord.product,
                    quantity = 0,
                    purchasePrice = salesRecord.price * 0.7, // Estimate 30% margin
                    sellingPrice = salesRecord.price
                )
                
                // Create invoice item (invoice will be created separately)
                val invoiceItem = InvoiceItem(
                    invoiceId = 0, // Will be set when invoice is created
                    stockId = stockItem.stockId,
                    quantity = salesRecord.quantity,
                    unitPrice = salesRecord.price,
                    totalPrice = salesRecord.total
                )
                
                Triple(customer, stockItem, invoiceItem)
                
            } catch (e: Exception) {
                Log.e("SalesDataService", "Error converting sales record to entities", e)
                Triple(null, null, null)
            }
        }
    }
    
    /**
     * Convert SalesRecord back to database entities for import
     * This is used when importing data from Excel
     */
    suspend fun convertToEntities(
        salesRecords: List<SalesRecord>
    ): Triple<List<Invoice>, List<InvoiceItem>, List<Customer>> = withContext(Dispatchers.IO) {
        
        val existingCustomers = customerRepository.allCustomers.first()
        val existingStock = stockRepository.allStockItems.first()
        
        val customerMap = existingCustomers.associateBy { it.name }
        val stockMap = existingStock.associateBy { it.name }
        
        val newCustomers = mutableListOf<Customer>()
        val invoices = mutableListOf<Invoice>()
        val invoiceItems = mutableListOf<InvoiceItem>()
        
        // Group sales records by date and customer to create invoices
        val groupedRecords = salesRecords.groupBy { "${it.date}_${it.customerName}" }
        
        groupedRecords.forEach { (_, records) ->
            val firstRecord = records.first()
            
            // Find or create customer
            var customer = customerMap[firstRecord.customerName]
            if (customer == null) {
                customer = Customer(
                    name = firstRecord.customerName,
                    pib = "",
                    phone = "",
                    email = "",
                    address = "",
                    totalDebt = 0.0
                )
                newCustomers.add(customer)
            }
            
            // Create invoice
            val totalAmount = records.sumOf { it.total }
            val invoice = Invoice(
                customerId = customer.customerId,
                date = firstRecord.date,
                totalAmount = totalAmount,
                paidAmount = totalAmount, // Assume imported data is paid
                status = "paid"
            )
            invoices.add(invoice)
            
            // Create invoice items
            records.forEach { record ->
                val stockItem = stockMap[record.product]
                if (stockItem != null) {
                    val item = InvoiceItem(
                        invoiceId = 0, // Will be set when invoice is inserted
                        stockId = stockItem.stockId,
                        quantity = record.quantity,
                        unitPrice = record.price,
                        totalPrice = record.total
                    )
                    invoiceItems.add(item)
                }
            }
        }
        
        Triple(invoices, invoiceItems, newCustomers)
    }
}