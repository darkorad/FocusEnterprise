package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.Invoice
import com.focusenterprise.data.InvoiceItem
import com.focusenterprise.data.repositories.InvoiceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InvoiceViewModel(private val repository: InvoiceRepository) : ViewModel() {

    val allInvoices: StateFlow<List<Invoice>> = repository.allInvoices
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertInvoiceWithItems(invoice: Invoice, items: List<InvoiceItem>) = viewModelScope.launch {
        repository.insertInvoiceWithItems(invoice, items)
    }
}
