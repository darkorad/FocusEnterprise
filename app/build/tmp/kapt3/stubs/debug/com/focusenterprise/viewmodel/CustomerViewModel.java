package com.focusenterprise.viewmodel;

import androidx.lifecycle.ViewModel;
import com.focusenterprise.data.Customer;
import com.focusenterprise.data.CustomerMonthlySummary;
import com.focusenterprise.data.repositories.CustomerRepository;
import com.focusenterprise.data.repositories.InvoiceRepository;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nJ\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nJ\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nJ\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nR\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/focusenterprise/viewmodel/CustomerViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/focusenterprise/data/repositories/CustomerRepository;", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "(Lcom/focusenterprise/data/repositories/CustomerRepository;Lcom/focusenterprise/data/repositories/InvoiceRepository;)V", "allCustomers", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/focusenterprise/data/Customer;", "getAllCustomers", "()Lkotlinx/coroutines/flow/StateFlow;", "customerMonthlySummaries", "Lcom/focusenterprise/data/CustomerMonthlySummary;", "getCustomerMonthlySummaries", "delete", "Lkotlinx/coroutines/Job;", "customer", "insert", "insertCustomer", "update", "app_debug"})
public final class CustomerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.CustomerRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Customer>> allCustomers = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.CustomerMonthlySummary>> customerMonthlySummaries = null;
    
    public CustomerViewModel(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.CustomerRepository repository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.Customer>> getAllCustomers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.CustomerMonthlySummary>> getCustomerMonthlySummaries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job insert(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Customer customer) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job update(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Customer customer) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job delete(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Customer customer) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job insertCustomer(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Customer customer) {
        return null;
    }
}