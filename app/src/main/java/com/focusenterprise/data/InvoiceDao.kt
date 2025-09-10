package com.focusenterprise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(invoice: Invoice): Long

    @Update
    fun update(invoice: Invoice)

    @Delete
    fun delete(invoice: Invoice)

    @Query("SELECT * FROM invoices ORDER BY date DESC")
    fun getAllInvoices(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoices WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getInvoicesByDateRange(startDate: Long, endDate: Long): Flow<List<Invoice>>

    @Query("SELECT COALESCE(SUM(totalAmount), 0.0) FROM invoices WHERE date BETWEEN :startDate AND :endDate")
    fun getMonthlySalesSum(startDate: Long, endDate: Long): Flow<Double>

    @Query("SELECT * FROM invoices WHERE customerId = :customerId ORDER BY date DESC")
    fun getInvoicesForCustomer(customerId: Int): Flow<List<Invoice>>

    @Query("SELECT * FROM invoices WHERE status != 'paid' ORDER BY date DESC")
    fun getActiveInvoices(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoices WHERE status = 'paid' ORDER BY date DESC")
    fun getCompletedInvoices(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoices WHERE customerId = :customerId AND status != 'paid' ORDER BY date DESC")
    fun getActiveInvoicesForCustomer(customerId: Int): Flow<List<Invoice>>

    @Query("SELECT * FROM invoices WHERE customerId = :customerId AND status = 'paid' ORDER BY date DESC")
    fun getCompletedInvoicesForCustomer(customerId: Int): Flow<List<Invoice>>

    @Query("""
        SELECT 
            i.customerId,
            c.name as customerName,
            strftime('%Y-%m', datetime(i.date/1000, 'unixepoch')) as monthYear,
            COALESCE(SUM(i.totalAmount), 0.0) as totalInvoiceAmount,
            COALESCE(SUM(i.paidAmount), 0.0) as totalPaidAmount,
            COUNT(i.invoiceId) as invoiceCount
        FROM invoices i
        INNER JOIN customers c ON i.customerId = c.customerId
        GROUP BY i.customerId, strftime('%Y-%m', datetime(i.date/1000, 'unixepoch'))
        ORDER BY monthYear DESC, c.name ASC
    """)
    fun getCustomerMonthlySummaries(): Flow<List<CustomerMonthlySummary>>
}
