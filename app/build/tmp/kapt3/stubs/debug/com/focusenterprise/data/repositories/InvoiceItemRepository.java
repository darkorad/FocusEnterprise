package com.focusenterprise.data.repositories;

import com.focusenterprise.data.InvoiceItem;
import com.focusenterprise.data.InvoiceItemDao;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\"\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015J \u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00110\u0007J\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0019\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u001b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/focusenterprise/data/repositories/InvoiceItemRepository;", "", "invoiceItemDao", "Lcom/focusenterprise/data/InvoiceItemDao;", "(Lcom/focusenterprise/data/InvoiceItemDao;)V", "allInvoiceItems", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/focusenterprise/data/InvoiceItem;", "getAllInvoiceItems", "()Lkotlinx/coroutines/flow/Flow;", "delete", "", "invoiceItem", "(Lcom/focusenterprise/data/InvoiceItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItemsForInvoice", "invoiceId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getItemsByDateRange", "startDate", "", "endDate", "getItemsByInvoiceIds", "invoiceIds", "getItemsForInvoice", "insert", "update", "app_debug"})
public final class InvoiceItemRepository {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.InvoiceItemDao invoiceItemDao = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.InvoiceItem>> allInvoiceItems = null;
    
    public InvoiceItemRepository(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceItemDao invoiceItemDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.InvoiceItem>> getAllInvoiceItems() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceItem invoiceItem, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceItem invoiceItem, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceItem invoiceItem, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.InvoiceItem>> getItemsForInvoice(int invoiceId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.InvoiceItem>> getItemsByDateRange(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteItemsForInvoice(int invoiceId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get invoice items for multiple invoice IDs
     * Used by SalesDataService to efficiently fetch items for multiple invoices
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.InvoiceItem>> getItemsByInvoiceIds(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> invoiceIds) {
        return null;
    }
}