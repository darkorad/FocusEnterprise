package com.focusenterprise

import android.app.Application
import com.focusenterprise.data.AppDatabase
import com.focusenterprise.data.repositories.*

class FocusEnterpriseApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    val customerRepository by lazy { CustomerRepository(database.customerDao()) }
    val stockRepository by lazy { StockRepository(database.stockItemDao()) }
    val invoiceRepository by lazy {
        InvoiceRepository(
            database.invoiceDao(),
            database.invoiceItemDao(),
            database.stockItemDao(),
            database.paymentDao(),
            database.customerDao()
        )
    }
    val expenseRepository by lazy { ExpenseRepository(database.expenseDao()) }
    val paymentRepository by lazy { PaymentRepository(database.paymentDao()) }
    val invoiceItemRepository by lazy { InvoiceItemRepository(database.invoiceItemDao()) }
}
