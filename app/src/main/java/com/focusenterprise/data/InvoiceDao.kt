package com.focusenterprise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(invoice: Invoice): Long

    @Update
    suspend fun update(invoice: Invoice)

    @Delete
    suspend fun delete(invoice: Invoice)

    @Query("SELECT * FROM invoices ORDER BY date DESC")
    fun getAllInvoices(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoices WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getInvoicesByDateRange(startDate: Long, endDate: Long): Flow<List<Invoice>>

    @Query("SELECT SUM(totalAmount) FROM invoices WHERE date BETWEEN :startDate AND :endDate")
    fun getMonthlySalesSum(startDate: Long, endDate: Long): Flow<Double>

    @Query("SELECT * FROM invoices WHERE customerId = :customerId ORDER BY date DESC")
    fun getInvoicesForCustomer(customerId: Int): Flow<List<Invoice>>
}
