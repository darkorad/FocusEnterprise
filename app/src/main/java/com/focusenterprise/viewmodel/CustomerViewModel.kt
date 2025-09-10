package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.Customer
import com.focusenterprise.data.CustomerMonthlySummary
import com.focusenterprise.data.repositories.CustomerRepository
import com.focusenterprise.data.repositories.InvoiceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CustomerViewModel(
    private val repository: CustomerRepository,
    private val invoiceRepository: InvoiceRepository
) : ViewModel() {

    val allCustomers: StateFlow<List<Customer>> = repository.allCustomers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val customerMonthlySummaries: StateFlow<List<CustomerMonthlySummary>> = invoiceRepository.getCustomerMonthlySummaries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(customer: Customer) = viewModelScope.launch {
        repository.insert(customer)
    }

    fun update(customer: Customer) = viewModelScope.launch {
        repository.update(customer)
    }

    fun delete(customer: Customer) = viewModelScope.launch {
        repository.delete(customer)
    }

    fun insertCustomer(customer: Customer) = viewModelScope.launch {
        repository.insert(customer)
    }
}
