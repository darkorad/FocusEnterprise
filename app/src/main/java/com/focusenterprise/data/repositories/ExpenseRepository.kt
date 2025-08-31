package com.focusenterprise.data.repositories

import com.focusenterprise.data.Expense
import com.focusenterprise.data.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    fun getExpensesByDateRange(startDate: Long, endDate: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesByDateRange(startDate, endDate)
    }

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }

    fun getMonthlyExpensesSum(startDate: Long, endDate: Long): Flow<Double> {
        return expenseDao.getMonthlyExpensesSum(startDate, endDate)
    }
}
