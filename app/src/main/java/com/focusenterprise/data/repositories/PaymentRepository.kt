package com.focusenterprise.data.repositories

import com.focusenterprise.data.Payment
import com.focusenterprise.data.PaymentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PaymentRepository(private val paymentDao: PaymentDao) {

    val allPayments: Flow<List<Payment>> = paymentDao.getAllPayments()

    fun getPaymentsForInvoice(invoiceId: Int): Flow<List<Payment>> {
        return paymentDao.getPaymentsForInvoice(invoiceId)
    }

    suspend fun insert(payment: Payment) {
        withContext(Dispatchers.IO) {
            paymentDao.insert(payment)
        }
    }

    fun getMonthlyPaymentsSum(startDate: Long, endDate: Long): Flow<Double> {
        return paymentDao.getMonthlyPaymentsSum(startDate, endDate)
    }

    fun getPaymentsForCustomer(customerId: Int): Flow<List<Payment>> {
        return paymentDao.getPaymentsForCustomer(customerId)
    }
}
