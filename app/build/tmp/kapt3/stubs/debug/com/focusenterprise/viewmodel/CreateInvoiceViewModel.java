package com.focusenterprise.viewmodel;

import androidx.lifecycle.ViewModel;
import com.focusenterprise.data.Customer;
import com.focusenterprise.data.Invoice;
import com.focusenterprise.data.InvoiceItem;
import com.focusenterprise.data.StockItem;
import com.focusenterprise.data.repositories.CustomerRepository;
import com.focusenterprise.data.repositories.InvoiceRepository;
import com.focusenterprise.data.repositories.StockRepository;
import kotlinx.coroutines.flow.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\'J\u000e\u0010(\u001a\u00020$2\u0006\u0010)\u001a\u00020\fJ\u0006\u0010*\u001a\u00020$J\u000e\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020\u000eR\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000b0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0019\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0019\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019\u00a8\u0006-"}, d2 = {"Lcom/focusenterprise/viewmodel/CreateInvoiceViewModel;", "Landroidx/lifecycle/ViewModel;", "invoiceRepository", "Lcom/focusenterprise/data/repositories/InvoiceRepository;", "customerRepository", "Lcom/focusenterprise/data/repositories/CustomerRepository;", "stockRepository", "Lcom/focusenterprise/data/repositories/StockRepository;", "(Lcom/focusenterprise/data/repositories/InvoiceRepository;Lcom/focusenterprise/data/repositories/CustomerRepository;Lcom/focusenterprise/data/repositories/StockRepository;)V", "_lineItems", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/focusenterprise/data/InvoiceItem;", "_selectedCustomer", "Lcom/focusenterprise/data/Customer;", "_validationError", "", "allCustomers", "Lkotlinx/coroutines/flow/Flow;", "getAllCustomers", "()Lkotlinx/coroutines/flow/Flow;", "allStockItems", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/focusenterprise/data/StockItem;", "getAllStockItems", "()Lkotlinx/coroutines/flow/StateFlow;", "lineItems", "getLineItems", "selectedCustomer", "getSelectedCustomer", "totalAmount", "", "getTotalAmount", "validationError", "getValidationError", "addLineItem", "", "stockItem", "quantity", "", "removeLineItem", "item", "saveInvoice", "setCustomer", "customer", "app_debug"})
public final class CreateInvoiceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.CustomerRepository customerRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.repositories.StockRepository stockRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Customer>> allCustomers = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.StockItem>> allStockItems = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.focusenterprise.data.Customer> _selectedCustomer = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.focusenterprise.data.Customer> selectedCustomer = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.focusenterprise.data.InvoiceItem>> _lineItems = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.InvoiceItem>> lineItems = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _validationError = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> validationError = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Double> totalAmount = null;
    
    public CreateInvoiceViewModel(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.InvoiceRepository invoiceRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.CustomerRepository customerRepository, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.repositories.StockRepository stockRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Customer>> getAllCustomers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.StockItem>> getAllStockItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.focusenterprise.data.Customer> getSelectedCustomer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.focusenterprise.data.InvoiceItem>> getLineItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getValidationError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Double> getTotalAmount() {
        return null;
    }
    
    public final void setCustomer(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Customer customer) {
    }
    
    public final void addLineItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.StockItem stockItem, int quantity) {
    }
    
    public final void removeLineItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceItem item) {
    }
    
    public final void saveInvoice() {
    }
}