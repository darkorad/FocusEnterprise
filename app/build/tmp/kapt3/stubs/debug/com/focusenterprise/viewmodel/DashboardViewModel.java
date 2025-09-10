package com.focusenterprise.viewmodel;

import androidx.lifecycle.ViewModel;
import com.focusenterprise.data.repositories.CustomerRepository;
import com.focusenterprise.data.repositories.ExpenseRepository;
import com.focusenterprise.data.repositories.InvoiceRepository;
import com.focusenterprise.data.repositories.StockRepository;
import com.focusenterprise.ui.screens.Activity;
import kotlinx.coroutines.flow.Flow;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010J\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010J\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0010J\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010J\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00160\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/focusenterprise/viewmodel/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "customerRepository", "Lcom/focusenterprise/data/repositories/CustomerRepository;", "expenseRepository", "Lcom/focusenterprise/data/repositories/ExpenseRepository;", "stockRepository", "Lcom/focusenterprise/data/repositories/StockRepository;", "(Lcom/focusenterprise/data/repositories/InvoiceRepository;Lcom/focusenterprise/data/repositories/CustomerRepository;Lcom/focusenterprise/data/repositories/ExpenseRepository;Lcom/focusenterprise/data/repositories/StockRepository;)V", "formatTimestamp", "", "timestamp", "", "getLowStockItems", "Lkotlinx/coroutines/flow/Flow;", "", "getMonthlyRevenue", "", "getPendingInvoices", "getRecentActivities", "", "Lcom/focusenterprise/ui/screens/Activity;", "getTodayRevenue", "getTotalCustomers", "getWeeklySalesData", "app_debug"})
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.CustomerRepository customerRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.ExpenseRepository expenseRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.StockRepository stockRepository = null;
    
    public DashboardViewModel(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.CustomerRepository customerRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.ExpenseRepository expenseRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.StockRepository stockRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Double> getTodayRevenue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlyRevenue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalCustomers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getPendingInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getLowStockItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.ui.screens.Activity>> getRecentActivities() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.Double>> getWeeklySalesData() {
        return null;
    }
    
    private final java.lang.String formatTimestamp(long timestamp) {
        return null;
    }
}