package com.focusenterprise.data;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J$\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\'J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\'J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u0010"}, d2 = {"Lcom/focusenterprise/data/ExpenseDao;", "", "delete", "", "expense", "Lcom/focusenterprise/data/Expense;", "getExpensesByDateRange", "Lkotlinx/coroutines/flow/Flow;", "", "startDate", "", "endDate", "getMonthlyExpensesSum", "", "insert", "update", "app_debug"})
@androidx.room.Dao
public abstract interface ExpenseDao {
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insert(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Expense expense);
    
    @androidx.room.Update
    public abstract void update(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Expense expense);
    
    @androidx.room.Delete
    public abstract void delete(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Expense expense);
    
    @androidx.room.Query(value = "SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Expense>> getExpensesByDateRange(long startDate, long endDate);
    
    @androidx.room.Query(value = "SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE date BETWEEN :startDate AND :endDate")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlyExpensesSum(long startDate, long endDate);
}