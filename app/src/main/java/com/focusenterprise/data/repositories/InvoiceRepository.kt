package com.focusenterprise.data.repositories

import com.focusenterprise.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class InvoiceRepository(
    private val invoiceDao: InvoiceDao,
    private val invoiceItemDao: InvoiceItemDao,
    private val stockItemDao: StockItemDao,
    private val paymentDao: PaymentDao,
    private val customerDao: CustomerDao
) {

    val allInvoices: Flow<List<Invoice>> = invoiceDao.getAllInvoices()

    fun getInvoicesByDateRange(startDate: Long, endDate: Long): Flow<List<Invoice>> {
        return invoiceDao.getInvoicesByDateRange(startDate, endDate)
    }

    fun getItemsForInvoice(invoiceId: Int): Flow<List<InvoiceItem>> {
        return invoiceItemDao.getItemsForInvoice(invoiceId)
    }

    suspend fun insertInvoiceWithItems(invoice: Invoice, items: List<InvoiceItem>) {
        val invoiceId = invoiceDao.insert(invoice)
        items.forEach { item ->
            invoiceItemDao.insert(item.copy(invoiceId = invoiceId.toInt()))
            stockItemDao.reduceStock(item.stockId, item.quantity)
        }
    }

    fun getMonthlySalesSum(startDate: Long, endDate: Long): Flow<Double> {
        return invoiceDao.getMonthlySalesSum(startDate, endDate)
    }

    fun getInvoicesForCustomer(customerId: Int): Flow<List<Invoice>> {
        return invoiceDao.getInvoicesForCustomer(customerId)
    }

    fun getItemsByDateRange(startDate: Long, endDate: Long): Flow<List<InvoiceItem>> {
        return invoiceItemDao.getItemsByDateRange(startDate, endDate)
    }

    suspend fun addPaymentToInvoice(invoice: Invoice, amount: Double) {
        // 1. Add payment
        val payment = Payment(
            invoiceId = invoice.invoiceId,
            amount = amount,
            date = System.currentTimeMillis()
        )
        paymentDao.insert(payment)

        // 2. Update invoice
        val newPaidAmount = invoice.paidAmount + amount
        val newStatus = if (newPaidAmount >= invoice.totalAmount) "paid" else "partial"
        val updatedInvoice = invoice.copy(
            paidAmount = newPaidAmount,
            status = newStatus
        )
        invoiceDao.update(updatedInvoice)

        // 3. Update customer debt
        val customer = customerDao.getCustomerById(invoice.customerId).first()
        val updatedCustomer = customer.copy(
            totalDebt = customer.totalDebt - amount
        )
        customerDao.update(updatedCustomer)
    }
}
