package com.focusenterprise.data.repositories

import com.focusenterprise.data.Payment
import com.focusenterprise.data.PaymentDao
import kotlinx.coroutines.flow.Flow

class PaymentRepository(private val paymentDao: PaymentDao) {

    fun getPaymentsForInvoice(invoiceId: Int): Flow<List<Payment>> {
        return paymentDao.getPaymentsForInvoice(invoiceId)
    }

    suspend fun insert(payment: Payment) {
        paymentDao.insert(payment)
    }

    fun getMonthlyPaymentsSum(startDate: Long, endDate: Long): Flow<Double> {
        return paymentDao.getMonthlyPaymentsSum(startDate, endDate)
    }

    fun getPaymentsForCustomer(customerId: Int): Flow<List<Payment>> {
        return paymentDao.getPaymentsForCustomer(customerId)
    }
}
