package com.focusenterprise.viewmodel;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.focusenterprise.FocusEnterpriseApplication;
import com.focusenterprise.data.Invoice;
import com.focusenterprise.data.repositories.CustomerRepository;
import com.focusenterprise.data.repositories.InvoiceRepository;
import com.focusenterprise.data.repositories.PaymentRepository;
import com.focusenterprise.util.PdfGenerator;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0012R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000f\u00a8\u0006\u001c"}, d2 = {"Lcom/focusenterprise/viewmodel/CustomerDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "customerId", "", "customerRepository", "Lcom/focusenterprise/data/repositories/CustomerRepository;", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "paymentRepository", "Lcom/focusenterprise/data/repositories/PaymentRepository;", "(ILcom/focusenterprise/data/repositories/CustomerRepository;Lcom/focusenterprise/data/repositories/InvoiceRepository;Lcom/focusenterprise/data/repositories/PaymentRepository;)V", "customer", "Lkotlinx/coroutines/flow/Flow;", "Lcom/focusenterprise/data/Customer;", "getCustomer", "()Lkotlinx/coroutines/flow/Flow;", "invoices", "", "Lcom/focusenterprise/data/Invoice;", "getInvoices", "payments", "Lcom/focusenterprise/data/Payment;", "getPayments", "exportInvoiceToPdf", "", "context", "Landroid/content/Context;", "invoice", "app_debug"})
public final class CustomerDetailViewModel extends androidx.lifecycle.ViewModel {
    private final int customerId = 0;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.CustomerRepository customerRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.PaymentRepository paymentRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.focusenterprise.data.Customer> customer = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> invoices = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> payments = null;
    
    public CustomerDetailViewModel(int customerId, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.CustomerRepository customerRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.PaymentRepository paymentRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.focusenterprise.data.Customer> getCustomer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getInvoices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> getPayments() {
        return null;
    }
    
    public final void exportInvoiceToPdf(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice) {
    }
}