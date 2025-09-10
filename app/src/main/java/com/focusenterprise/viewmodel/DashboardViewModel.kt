package com.focusenterprise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusenterprise.data.repositories.CustomerRepository
import com.focusenterprise.data.repositories.ExpenseRepository
import com.focusenterprise.data.repositories.InvoiceRepository
import com.focusenterprise.data.repositories.StockRepository
import com.focusenterprise.ui.screens.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel constructor(
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    private val expenseRepository: ExpenseRepository,
    private val stockRepository: StockRepository
) : ViewModel() {

    fun getTodayRevenue(): Flow<Double> {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        
        return invoiceRepository.getMonthlySalesSum(
            startOfDay.timeInMillis,
            endOfDay.timeInMillis
        )
    }

    fun getMonthlyRevenue(): Flow<Double> {
        val startOfMonth = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endOfMonth = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        
        return invoiceRepository.getMonthlySalesSum(
            startOfMonth.timeInMillis,
            endOfMonth.timeInMillis
        )
    }

    fun getTotalCustomers(): Flow<Int> {
        return customerRepository.allCustomers.map { it.size }
    }

    fun getPendingInvoices(): Flow<Int> {
        // Assuming we have a status field in invoices
        return flowOf(0) // Placeholder - would need to implement invoice status tracking
    }

    fun getLowStockItems(): Flow<Int> {
        return stockRepository.allStockItems.map { items ->
            items.count { it.quantity <= 5 } // Consider items with 5 or less as low stock
        }
    }

    fun getRecentActivities(): Flow<List<Activity>> {
        return flow {
            val activities = mutableListOf<Activity>()
            
            // Get recent invoices (last 5)
            invoiceRepository.allInvoices.collect { invoices ->
                invoices.take(5).forEach { invoice ->
                    activities.add(
                        Activity(
                            title = "Invoice Created",
                            description = "Invoice #${invoice.invoiceId} - RSD ${String.format("%.2f", invoice.totalAmount)}",
                            timestamp = formatTimestamp(invoice.date),
                            icon = Icons.Filled.Receipt
                        )
                    )
                }
            }
            
            // Get recent customers (last 3)
            customerRepository.allCustomers.collect { customers ->
                customers.take(3).forEach { customer ->
                    activities.add(
                        Activity(
                            title = "Customer",
                            description = "${customer.name}",
                            timestamp = "Customer Record",
                            icon = Icons.Filled.PersonAdd
                        )
                    )
                }
            }
            
            // Limit to 8 activities
            emit(activities.take(8))
        }
    }

    fun getWeeklySalesData(): Flow<List<Double>> {
        return flow {
            val salesData = mutableListOf<Double>()
            
            // Get sales for the last 7 days
            for (i in 6 downTo 0) {
                val dayCalendar = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -i)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                
                val endDayCalendar = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -i)
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }
                
                val dailySales = invoiceRepository.getMonthlySalesSum(
                    dayCalendar.timeInMillis,
                    endDayCalendar.timeInMillis
                )
                
                dailySales.collect { sales ->
                    salesData.add(sales)
                }
            }
            
            emit(salesData)
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60000 -> "Just now"
            diff < 3600000 -> "${diff / 60000}m ago"
            diff < 86400000 -> "${diff / 3600000}h ago"
            else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(timestamp))
        }
    }
}