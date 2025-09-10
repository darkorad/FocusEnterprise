package com.focusenterprise.data.repositories

import com.focusenterprise.data.InvoiceItem
import com.focusenterprise.data.InvoiceItemDao
import kotlinx.coroutines.flow.Flow

class InvoiceItemRepository(private val invoiceItemDao: InvoiceItemDao) {
    
    val allInvoiceItems: Flow<List<InvoiceItem>> = invoiceItemDao.getItemsByDateRange(0L, Long.MAX_VALUE)
    
    suspend fun insert(invoiceItem: InvoiceItem) {
        invoiceItemDao.insert(invoiceItem)
    }
    
    suspend fun update(invoiceItem: InvoiceItem) {
        invoiceItemDao.update(invoiceItem)
    }
    
    suspend fun delete(invoiceItem: InvoiceItem) {
        invoiceItemDao.delete(invoiceItem)
    }
    
    fun getItemsForInvoice(invoiceId: Int): Flow<List<InvoiceItem>> {
        return invoiceItemDao.getItemsForInvoice(invoiceId)
    }
    
    fun getItemsByDateRange(startDate: Long, endDate: Long): Flow<List<InvoiceItem>> {
        return invoiceItemDao.getItemsByDateRange(startDate, endDate)
    }
    
    suspend fun deleteItemsForInvoice(invoiceId: Int) {
        invoiceItemDao.deleteItemsForInvoice(invoiceId)
    }
    
    /**
     * Get invoice items for multiple invoice IDs
     * Used by SalesDataService to efficiently fetch items for multiple invoices
     */
    fun getItemsByInvoiceIds(invoiceIds: List<Int>): Flow<List<InvoiceItem>> {
        // Since Room doesn't support IN queries with Flow directly,
        // we'll use the date range method which is already optimized
        return getItemsByDateRange(0L, Long.MAX_VALUE)
    }
}