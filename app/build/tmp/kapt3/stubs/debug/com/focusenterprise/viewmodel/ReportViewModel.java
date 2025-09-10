package com.focusenterprise.viewmodel;

import androidx.lifecycle.ViewModel;
import com.focusenterprise.data.InvoiceItem;
import com.focusenterprise.data.repositories.ExpenseRepository;
import com.focusenterprise.data.repositories.InvoiceRepository;
import com.focusenterprise.data.repositories.StockRepository;
import kotlinx.coroutines.flow.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rJ\"\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/focusenterprise/viewmodel/ReportViewModel;", "Landroidx/lifecycle/ViewModel;", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "expenseRepository", "Lcom/focusenterprise/data/repositories/ExpenseRepository;", "stockRepository", "Lcom/focusenterprise/data/repositories/StockRepository;", "(Lcom/focusenterprise/data/repositories/InvoiceRepository;Lcom/focusenterprise/data/repositories/ExpenseRepository;Lcom/focusenterprise/data/repositories/StockRepository;)V", "getMonthlyExpensesSum", "Lkotlinx/coroutines/flow/Flow;", "", "startDate", "", "endDate", "getMonthlySalesSum", "getSoldArticlesSummary", "", "Lcom/focusenterprise/viewmodel/SoldArticleSummary;", "app_debug"})
public final class ReportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.ExpenseRepository expenseRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.StockRepository stockRepository = null;
    
    public ReportViewModel(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.ExpenseRepository expenseRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.StockRepository stockRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlySalesSum(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlyExpensesSum(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.viewmodel.SoldArticleSummary>> getSoldArticlesSummary(long startDate, long endDate) {
        return null;
    }
}