package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.focusenterprise.FocusEnterpriseApplication

class ViewModelFactory(private val application: FocusEnterpriseApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StockViewModel::class.java) ->
                StockViewModel(application.stockRepository) as T
            modelClass.isAssignableFrom(CustomerViewModel::class.java) ->
                CustomerViewModel(application.customerRepository) as T
            modelClass.isAssignableFrom(InvoiceViewModel::class.java) ->
                InvoiceViewModel(application.invoiceRepository) as T
            modelClass.isAssignableFrom(ExpenseViewModel::class.java) ->
                ExpenseViewModel(application.expenseRepository) as T
            modelClass.isAssignableFrom(ReportViewModel::class.java) ->
                ReportViewModel(application.invoiceRepository, application.expenseRepository, application.stockRepository) as T
            modelClass.isAssignableFrom(PaymentViewModel::class.java) ->
                PaymentViewModel(application.invoiceRepository) as T
            modelClass.isAssignableFrom(CreateInvoiceViewModel::class.java) ->
                CreateInvoiceViewModel(application.invoiceRepository, application.customerRepository, application.stockRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
