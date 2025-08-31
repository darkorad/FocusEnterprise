package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.focusenterprise.FocusEnterpriseApplication
import com.focusenterprise.data.repositories.CustomerRepository
import com.focusenterprise.data.repositories.InvoiceRepository
import com.focusenterprise.data.repositories.PaymentRepository

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.Invoice
import com.focusenterprise.util.PdfGenerator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CustomerDetailViewModel(
    private val customerId: Int,
    private val customerRepository: CustomerRepository,
    private val invoiceRepository: InvoiceRepository,
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    val customer = customerRepository.getCustomerById(customerId)
    val invoices = invoiceRepository.getInvoicesForCustomer(customerId)
    val payments = paymentRepository.getPaymentsForCustomer(customerId)

    fun exportInvoiceToPdf(context: Context, invoice: Invoice) {
        viewModelScope.launch {
            val items = invoiceRepository.getItemsForInvoice(invoice.invoiceId).first()
            val customerData = customer.first()
            if (customerData != null) {
                PdfGenerator.generateInvoicePdf(context, invoice, items, customerData)
            }
        }
    }
}

class CustomerDetailViewModelFactory(
    private val customerId: Int,
    private val application: FocusEnterpriseApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerDetailViewModel(
                customerId,
                application.customerRepository,
                application.invoiceRepository,
                application.paymentRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
