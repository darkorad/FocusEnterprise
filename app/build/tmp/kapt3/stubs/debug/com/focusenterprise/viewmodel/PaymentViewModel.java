package com.focusenterprise.viewmodel;

import androidx.lifecycle.ViewModel;
import com.focusenterprise.data.Invoice;
import com.focusenterprise.data.repositories.InvoiceRepository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/focusenterprise/viewmodel/PaymentViewModel;", "Landroidx/lifecycle/ViewModel;", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "(Lcom/focusenterprise/data/repositories/InvoiceRepository;)V", "addPayment", "", "invoice", "Lcom/focusenterprise/data/Invoice;", "amount", "", "app_debug"})
public final class PaymentViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository = null;
    
    public PaymentViewModel(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository) {
        super();
    }
    
    public final void addPayment(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, double amount) {
    }
}