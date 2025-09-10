package com.focusenterprise;

import android.app.Application;
import com.focusenterprise.data.AppDatabase;
import com.focusenterprise.data.repositories.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u000f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u00148FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\b\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0018\u001a\u00020\u00198FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\b\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u001e8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b!\u0010\b\u001a\u0004\b\u001f\u0010 R\u001b\u0010\"\u001a\u00020#8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b&\u0010\b\u001a\u0004\b$\u0010%\u00a8\u0006\'"}, d2 = {"Lcom/focusenterprise/FocusEnterpriseApplication;", "Landroid/app/Application;", "()V", "customerRepository", "Lcom/focusenterprise/data/repositories/CustomerRepository;", "getCustomerRepository", "()Lcom/focusenterprise/data/repositories/CustomerRepository;", "customerRepository$delegate", "Lkotlin/Lazy;", "database", "Lcom/focusenterprise/data/AppDatabase;", "getDatabase", "()Lcom/focusenterprise/data/AppDatabase;", "database$delegate", "expenseRepository", "Lcom/focusenterprise/data/repositories/ExpenseRepository;", "getExpenseRepository", "()Lcom/focusenterprise/data/repositories/ExpenseRepository;", "expenseRepository$delegate", "invoiceItemRepository", "Lcom/focusenterprise/data/repositories/InvoiceItemRepository;", "getInvoiceItemRepository", "()Lcom/focusenterprise/data/repositories/InvoiceItemRepository;", "invoiceItemRepository$delegate", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "getInvoiceRepository", "()Lcom/focusenterprise/data/repositories/InvoiceRepository;", "invoiceRepository$delegate", "paymentRepository", "Lcom/focusenterprise/data/repositories/PaymentRepository;", "getPaymentRepository", "()Lcom/focusenterprise/data/repositories/PaymentRepository;", "paymentRepository$delegate", "stockRepository", "Lcom/focusenterprise/data/repositories/StockRepository;", "getStockRepository", "()Lcom/focusenterprise/data/repositories/StockRepository;", "stockRepository$delegate", "app_debug"})
public final class FocusEnterpriseApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy customerRepository$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy stockRepository$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy invoiceRepository$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy expenseRepository$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy paymentRepository$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy invoiceItemRepository$delegate = null;
    
    public FocusEnterpriseApplication() {
        super();
    }
    
    private final com.focusenterprise.data.AppDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.focusenterprise.data.repositories.CustomerRepository getCustomerRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.focusenterprise.data.repositories.StockRepository getStockRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.focusenterprise.data.repositories.InvoiceRepository getInvoiceRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.focusenterprise.data.repositories.ExpenseRepository getExpenseRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.focusenterprise.data.repositories.PaymentRepository getPaymentRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.focusenterprise.data.repositories.InvoiceItemRepository getInvoiceItemRepository() {
        return null;
    }
}