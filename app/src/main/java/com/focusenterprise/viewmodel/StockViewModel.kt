package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.StockItem
import com.focusenterprise.data.repositories.StockRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    val allStockItems: StateFlow<List<StockItem>> = repository.allStockItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(stockItem: StockItem) = viewModelScope.launch {
        repository.insert(stockItem)
    }

    fun update(stockItem: StockItem) = viewModelScope.launch {
        repository.update(stockItem)
    }

    fun delete(stockItem: StockItem) = viewModelScope.launch {
        repository.delete(stockItem)
    }
}
