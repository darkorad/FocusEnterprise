package com.focusenterprise.data;

import com.focusenterprise.data.repositories.InvoiceRepository;
import com.focusenterprise.data.repositories.CustomerRepository;
import com.focusenterprise.data.repositories.StockRepository;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.Dispatchers;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJC\u0010\t\u001a&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u000bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u000bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\"\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017J1\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2 = {"Lcom/focusenterprise/data/SalesDataService;", "", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "customerRepository", "Lcom/focusenterprise/data/repositories/CustomerRepository;", "stockRepository", "Lcom/focusenterprise/data/repositories/StockRepository;", "(Lcom/focusenterprise/data/repositories/InvoiceRepository;Lcom/focusenterprise/data/repositories/CustomerRepository;Lcom/focusenterprise/data/repositories/StockRepository;)V", "convertToEntities", "Lkotlin/Triple;", "", "Lcom/focusenterprise/data/Invoice;", "Lcom/focusenterprise/data/InvoiceItem;", "Lcom/focusenterprise/data/Customer;", "salesRecords", "Lcom/focusenterprise/data/SalesRecord;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSalesRecords", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSalesRecords", "Lkotlinx/coroutines/flow/Flow;", "startDate", "", "endDate", "getSalesRecordsChunked", "chunkSize", "", "(JJILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SalesDataService {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.CustomerRepository customerRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.StockRepository stockRepository = null;
    
    public SalesDataService(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.CustomerRepository customerRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.StockRepository stockRepository) {
        super();
    }
    
    /**
     * Get aggregated sales data for a date range
     * Combines invoice items with customer and stock information
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.SalesRecord>> getSalesRecords(long startDate, long endDate) {
        return null;
    }
    
    /**
     * Get sales records with memory optimization for large datasets
     * Processes data in chunks to prevent memory issues
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getSalesRecordsChunked(long startDate, long endDate, int chunkSize, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.focusenterprise.data.SalesRecord>> $completion) {
        return null;
    }
    
    /**
     * Get all sales records (for full export)
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getAllSalesRecords(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.focusenterprise.data.SalesRecord>> $completion) {
        return null;
    }
    
    /**
     * Convert SalesRecord back to database entities for import
     * This is used when importing data from Excel
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object convertToEntities(@org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.SalesRecord> salesRecords, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Triple<? extends java.util.List<com.focusenterprise.data.Invoice>, ? extends java.util.List<com.focusenterprise.data.InvoiceItem>, ? extends java.util.List<com.focusenterprise.data.Customer>>> $completion) {
        return null;
    }
}