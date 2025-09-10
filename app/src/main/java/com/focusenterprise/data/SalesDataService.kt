package com.focusenterprise.data

import com.focusenterprise.data.repositories.InvoiceRepository
import com.focusenterprise.data.repositories.CustomerRepository
import com.focusenterprise.data.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class SalesDataService(
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    private val stockRepository: StockRepository
) {
    
    /**
     * Get aggregated sales data for a date range
     * Combines invoice items with customer and stock information
     */
    fun getSalesRecords(startDate: Long, endDate: Long): Flow<List<SalesRecord>> {
        return combine(
            invoiceRepository.getInvoicesByDateRange(startDate, endDate),
            invoiceRepository.getItemsByDateRange(startDate, endDate),
            customerRepository.allCustomers,
            stockRepository.allStockItems
        ) { invoices, invoiceItems, customers, stockItems ->
            
            // Create maps for efficient lookup
            val invoiceMap = invoices.associateBy { it.invoiceId }
            val customerMap = customers.associateBy { it.customerId }
            val stockMap = stockItems.associateBy { it.stockId }
            
            // Convert invoice items to sales records
            invoiceItems.mapNotNull { item ->
                val invoice = invoiceMap[item.invoiceId]
                val customer = invoice?.let { customerMap[it.customerId] }
                val stockItem = stockMap[item.stockId]
                
                if (invoice != null && customer != null && stockItem != null) {
                    SalesRecord(
                        date = invoice.date,
                        customerName = customer.name,
                        product = stockItem.name,
                        quantity = item.quantity,
                        price = item.unitPrice,
                        total = item.totalPrice
                    )
                } else null
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
            val chunkItems = invoiceRepository.getItemsByDateRange(startDate, endDate).first()
                .filter { it.invoiceId in invoiceIds }
            
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
     * Get all sales records (for full export)
     */
    suspend fun getAllSalesRecords(): List<SalesRecord> = withContext(Dispatchers.IO) {
        getSalesRecordsChunked(0L, System.currentTimeMillis())
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
                    pib = null,
                    phone = null,
                    email = null,
                    address = null
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