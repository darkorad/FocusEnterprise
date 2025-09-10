package com.focusenterprise.data.repositories

import com.focusenterprise.data.Expense
import com.focusenterprise.data.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: Flow<List<Expense>> = expenseDao.getExpensesByDateRange(0, Long.MAX_VALUE)

    fun getExpensesByDateRange(startDate: Long, endDate: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesByDateRange(startDate, endDate)
    }

    suspend fun insert(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.insert(expense)
        }
    }

    suspend fun update(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.update(expense)
        }
    }

    suspend fun delete(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.delete(expense)
        }
    }

    fun getMonthlyExpensesSum(startDate: Long, endDate: Long): Flow<Double> {
        return expenseDao.getMonthlyExpensesSum(startDate, endDate)
    }
}
