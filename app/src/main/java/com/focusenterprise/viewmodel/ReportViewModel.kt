package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.InvoiceItem
import com.focusenterprise.data.repositories.ExpenseRepository
import com.focusenterprise.data.repositories.InvoiceRepository
import com.focusenterprise.data.repositories.StockRepository
import kotlinx.coroutines.flow.*

data class SoldArticleSummary(
    val stockId: Int,
    val name: String,
    val totalQuantity: Int,
    val totalValue: Double
)

class ReportViewModel(
    private val invoiceRepository: InvoiceRepository,
    private val expenseRepository: ExpenseRepository,
    private val stockRepository: StockRepository
) : ViewModel() {

    fun getMonthlySalesSum(startDate: Long, endDate: Long): Flow<Double> {
        return invoiceRepository.getMonthlySalesSum(startDate, endDate)
    }

    fun getMonthlyExpensesSum(startDate: Long, endDate: Long): Flow<Double> {
        return expenseRepository.getMonthlyExpensesSum(startDate, endDate)
    }

    fun getSoldArticlesSummary(startDate: Long, endDate: Long): Flow<List<SoldArticleSummary>> {
        return invoiceRepository.getItemsByDateRange(startDate, endDate)
            .combine(stockRepository.allStockItems) { invoiceItems, stockItems ->
                invoiceItems.groupBy { it.stockId }
                    .map { (stockId, items) ->
                        val stockItem = stockItems.find { it.stockId == stockId }
                        SoldArticleSummary(
                            stockId = stockId,
                            name = stockItem?.name ?: "Unknown",
                            totalQuantity = items.sumOf { it.quantity },
                            totalValue = items.sumOf { it.totalPrice }
                        )
                    }
            }
    }
}
