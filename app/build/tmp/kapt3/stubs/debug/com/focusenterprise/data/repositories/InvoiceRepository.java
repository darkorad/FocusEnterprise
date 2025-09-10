package com.focusenterprise.data.repositories;

import com.focusenterprise.data.*;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ!\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u0019\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u0012\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u000f0\u000eJ\"\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#J\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e2\u0006\u0010&\u001a\u00020\'J\"\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u000f0\u000e2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#J\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u000f0\u000e2\u0006\u0010+\u001a\u00020\'J\u001c\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001b0\u000e2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#J\'\u0010-\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00102\f\u0010.\u001a\b\u0012\u0004\u0012\u00020)0\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010/R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00060"}, d2 = {"Lcom/focusenterprise/data/repositories/InvoiceRepository;", "", "invoiceDao", "Lcom/focusenterprise/data/InvoiceDao;", "invoiceItemDao", "Lcom/focusenterprise/data/InvoiceItemDao;", "stockItemDao", "Lcom/focusenterprise/data/StockItemDao;", "paymentDao", "Lcom/focusenterprise/data/PaymentDao;", "customerDao", "Lcom/focusenterprise/data/CustomerDao;", "(Lcom/focusenterprise/data/InvoiceDao;Lcom/focusenterprise/data/InvoiceItemDao;Lcom/focusenterprise/data/StockItemDao;Lcom/focusenterprise/data/PaymentDao;Lcom/focusenterprise/data/CustomerDao;)V", "activeInvoices", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/focusenterprise/data/Invoice;", "getActiveInvoices", "()Lkotlinx/coroutines/flow/Flow;", "allInvoices", "getAllInvoices", "completedInvoices", "getCompletedInvoices", "addPaymentToInvoice", "", "invoice", "amount", "", "(Lcom/focusenterprise/data/Invoice;DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteInvoice", "(Lcom/focusenterprise/data/Invoice;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCustomerMonthlySummaries", "Lcom/focusenterprise/data/CustomerMonthlySummary;", "getInvoicesByDateRange", "startDate", "", "endDate", "getInvoicesForCustomer", "customerId", "", "getItemsByDateRange", "Lcom/focusenterprise/data/InvoiceItem;", "getItemsForInvoice", "invoiceId", "getMonthlySalesSum", "insertInvoiceWithItems", "items", "(Lcom/focusenterprise/data/Invoice;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class InvoiceRepository {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.InvoiceDao invoiceDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.InvoiceItemDao invoiceItemDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.StockItemDao stockItemDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.PaymentDao paymentDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.CustomerDao customerDao = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> allInvoices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> activeInvoices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> completedInvoices = null;
    
    public InvoiceRepository(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceDao invoiceDao, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceItemDao invoiceItemDao, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.StockItemDao stockItemDao, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.PaymentDao paymentDao, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.CustomerDao customerDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getAllInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getActiveInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getCompletedInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getInvoicesByDateRange(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.InvoiceItem>> getItemsForInvoice(int invoiceId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertInvoiceWithItems(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.InvoiceItem> items, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlySalesSum(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getInvoicesForCustomer(int customerId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.InvoiceItem>> getItemsByDateRange(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.CustomerMonthlySummary>> getCustomerMonthlySummaries() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addPaymentToInvoice(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, double amount, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteInvoice(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}