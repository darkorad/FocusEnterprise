package com.focusenterprise.data;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0014\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0007H\'J\u001c\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00072\u0006\u0010\n\u001a\u00020\u000bH\'J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0007H\'J\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0007H\'J\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00072\u0006\u0010\n\u001a\u00020\u000bH\'J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\b0\u0007H\'J$\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\'J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00072\u0006\u0010\n\u001a\u00020\u000bH\'J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\'J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u001a"}, d2 = {"Lcom/focusenterprise/data/InvoiceDao;", "", "delete", "", "invoice", "Lcom/focusenterprise/data/Invoice;", "getActiveInvoices", "Lkotlinx/coroutines/flow/Flow;", "", "getActiveInvoicesForCustomer", "customerId", "", "getAllInvoices", "getCompletedInvoices", "getCompletedInvoicesForCustomer", "getCustomerMonthlySummaries", "Lcom/focusenterprise/data/CustomerMonthlySummary;", "getInvoicesByDateRange", "startDate", "", "endDate", "getInvoicesForCustomer", "getMonthlySalesSum", "", "insert", "update", "app_debug"})
@androidx.room.Dao
public abstract interface InvoiceDao {
    
    @androidx.room.Insert(onConflict = 1)
    public abstract long insert(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice);
    
    @androidx.room.Update
    public abstract void update(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice);
    
    @androidx.room.Delete
    public abstract void delete(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice);
    
    @androidx.room.Query(value = "SELECT * FROM invoices ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getAllInvoices();
    
    @androidx.room.Query(value = "SELECT * FROM invoices WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getInvoicesByDateRange(long startDate, long endDate);
    
    @androidx.room.Query(value = "SELECT COALESCE(SUM(totalAmount), 0.0) FROM invoices WHERE date BETWEEN :startDate AND :endDate")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlySalesSum(long startDate, long endDate);
    
    @androidx.room.Query(value = "SELECT * FROM invoices WHERE customerId = :customerId ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getInvoicesForCustomer(int customerId);
    
    @androidx.room.Query(value = "SELECT * FROM invoices WHERE status != \'paid\' ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getActiveInvoices();
    
    @androidx.room.Query(value = "SELECT * FROM invoices WHERE status = \'paid\' ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getCompletedInvoices();
    
    @androidx.room.Query(value = "SELECT * FROM invoices WHERE customerId = :customerId AND status != \'paid\' ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getActiveInvoicesForCustomer(int customerId);
    
    @androidx.room.Query(value = "SELECT * FROM invoices WHERE customerId = :customerId AND status = \'paid\' ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Invoice>> getCompletedInvoicesForCustomer(int customerId);
    
    @androidx.room.Query(value = "\n        SELECT \n            i.customerId,\n            c.name as customerName,\n            strftime(\'%Y-%m\', datetime(i.date/1000, \'unixepoch\')) as monthYear,\n            COALESCE(SUM(i.totalAmount), 0.0) as totalInvoiceAmount,\n            COALESCE(SUM(i.paidAmount), 0.0) as totalPaidAmount,\n            COUNT(i.invoiceId) as invoiceCount\n        FROM invoices i\n        INNER JOIN customers c ON i.customerId = c.customerId\n        GROUP BY i.customerId, strftime(\'%Y-%m\', datetime(i.date/1000, \'unixepoch\'))\n        ORDER BY monthYear DESC, c.name ASC\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.CustomerMonthlySummary>> getCustomerMonthlySummaries();
}