package com.focusenterprise.viewmodel;

import androidx.lifecycle.ViewModel;
import com.focusenterprise.data.Invoice;
import com.focusenterprise.data.InvoiceItem;
import com.focusenterprise.data.repositories.InvoiceRepository;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bJ\u001c\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0007R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/focusenterprise/viewmodel/InvoiceViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "(Lcom/focusenterprise/data/repositories/InvoiceRepository;)V", "activeInvoices", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/focusenterprise/data/Invoice;", "getActiveInvoices", "()Lkotlinx/coroutines/flow/StateFlow;", "allInvoices", "getAllInvoices", "completedInvoices", "getCompletedInvoices", "deleteInvoice", "Lkotlinx/coroutines/Job;", "invoice", "insertInvoiceWithItems", "items", "Lcom/focusenterprise/data/InvoiceItem;", "app_debug"})
public final class InvoiceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Invoice>> allInvoices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Invoice>> activeInvoices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Invoice>> completedInvoices = null;
    
    public InvoiceViewModel(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Invoice>> getAllInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Invoice>> getActiveInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Invoice>> getCompletedInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job insertInvoiceWithItems(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.InvoiceItem> items) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job deleteInvoice(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice) {
        return null;
    }
}