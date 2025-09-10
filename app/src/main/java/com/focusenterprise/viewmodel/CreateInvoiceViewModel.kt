package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.Customer
import com.focusenterprise.data.Invoice
import com.focusenterprise.data.InvoiceItem
import com.focusenterprise.data.StockItem
import com.focusenterprise.data.repositories.CustomerRepository
import com.focusenterprise.data.repositories.InvoiceRepository
import com.focusenterprise.data.repositories.StockRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreateInvoiceViewModel(
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    private val stockRepository: StockRepository
) : ViewModel() {

    val allCustomers = customerRepository.allCustomers
    val allStockItems = stockRepository.allStockItems.stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    private val _selectedCustomer = MutableStateFlow<Customer?>(null)
    val selectedCustomer: StateFlow<Customer?> = _selectedCustomer

    private val _lineItems = MutableStateFlow<List<InvoiceItem>>(emptyList())
    val lineItems: StateFlow<List<InvoiceItem>> = _lineItems

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError

    val totalAmount = lineItems.map { items -> items.sumOf { it.totalPrice } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    fun setCustomer(customer: Customer) {
        _selectedCustomer.value = customer
    }

    fun addLineItem(stockItem: StockItem, quantity: Int) {
        val newItem = InvoiceItem(
            invoiceId = 0, // Will be set on save
            stockId = stockItem.stockId,
            quantity = quantity,
            unitPrice = stockItem.sellingPrice,
            totalPrice = stockItem.sellingPrice * quantity
        )
        _lineItems.value = _lineItems.value + newItem
    }

    fun removeLineItem(item: InvoiceItem) {
        _lineItems.value = _lineItems.value - item
    }

    fun saveInvoice() {
        viewModelScope.launch {
            val customer = _selectedCustomer.value
            if (customer == null) {
                _validationError.value = "Please select a customer."
                return@launch
            }

            if (_lineItems.value.isEmpty()) {
                _validationError.value = "Please add at least one item to the invoice."
                return@launch
            }

            // Stock validation
            val stockItems = allStockItems.value
            _lineItems.value.forEach { item ->
                val stockItem = stockItems.find { it.stockId == item.stockId }
                if (stockItem == null || stockItem.quantity < item.quantity) {
                    _validationError.value = "Not enough stock for ${stockItem?.name}. Available: ${stockItem?.quantity}, Requested: ${item.quantity}"
                    return@launch
                }
            }

            val invoice = Invoice(
                customerId = customer.customerId,
                date = System.currentTimeMillis(),
                totalAmount = totalAmount.value,
                paidAmount = 0.0,
                status = "unpaid"
            )

            invoiceRepository.insertInvoiceWithItems(invoice, _lineItems.value)
            _validationError.value = "Invoice Saved!" // Use this for success message
        }
    }
}
