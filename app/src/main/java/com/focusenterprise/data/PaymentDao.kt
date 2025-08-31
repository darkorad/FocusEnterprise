package com.focusenterprise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(payment: Payment)

    @Update
    suspend fun update(payment: Payment)

    @Delete
    suspend fun delete(payment: Payment)

    @Query("SELECT * FROM payments WHERE invoiceId = :invoiceId ORDER BY date DESC")
    fun getPaymentsForInvoice(invoiceId: Int): Flow<List<Payment>>

    @Query("SELECT SUM(amount) FROM payments WHERE date BETWEEN :startDate AND :endDate")
    fun getMonthlyPaymentsSum(startDate: Long, endDate: Long): Flow<Double>

    @Query("SELECT * FROM payments WHERE invoiceId IN (SELECT invoiceId FROM invoices WHERE customerId = :customerId)")
    fun getPaymentsForCustomer(customerId: Int): Flow<List<Payment>>
}
