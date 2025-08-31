package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.focusenterprise.FocusEnterpriseApplication
import com.focusenterprise.data.Invoice
import com.focusenterprise.data.repositories.InvoiceRepository
import kotlinx.coroutines.launch

class PaymentViewModel(private val invoiceRepository: InvoiceRepository) : ViewModel() {

    fun addPayment(invoice: Invoice, amount: Double) {
        viewModelScope.launch {
            invoiceRepository.addPaymentToInvoice(invoice, amount)
        }
    }
}
