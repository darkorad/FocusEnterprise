package com.focusenterprise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(invoiceItem: InvoiceItem)

    @Update
    fun update(invoiceItem: InvoiceItem)

    @Delete
    fun delete(invoiceItem: InvoiceItem)

    @Query("SELECT * FROM invoice_items WHERE invoiceId = :invoiceId")
    fun getItemsForInvoice(invoiceId: Int): Flow<List<InvoiceItem>>

    @Query("SELECT * FROM invoice_items WHERE invoiceId IN (SELECT invoiceId FROM invoices WHERE date BETWEEN :startDate AND :endDate)")
    fun getItemsByDateRange(startDate: Long, endDate: Long): Flow<List<InvoiceItem>>

    @Query("DELETE FROM invoice_items WHERE invoiceId = :invoiceId")
    fun deleteItemsForInvoice(invoiceId: Int)
}
