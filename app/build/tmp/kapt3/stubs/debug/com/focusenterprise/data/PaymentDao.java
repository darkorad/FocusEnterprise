package com.focusenterprise.data;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\nH\'J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\'J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\n2\u0006\u0010\u0012\u001a\u00020\bH\'J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\n2\u0006\u0010\u0007\u001a\u00020\bH\'J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u0016"}, d2 = {"Lcom/focusenterprise/data/PaymentDao;", "", "delete", "", "payment", "Lcom/focusenterprise/data/Payment;", "deletePaymentsForInvoice", "invoiceId", "", "getAllPayments", "Lkotlinx/coroutines/flow/Flow;", "", "getMonthlyPaymentsSum", "", "startDate", "", "endDate", "getPaymentsForCustomer", "customerId", "getPaymentsForInvoice", "insert", "update", "app_debug"})
@androidx.room.Dao
public abstract interface PaymentDao {
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insert(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Payment payment);
    
    @androidx.room.Update
    public abstract void update(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Payment payment);
    
    @androidx.room.Delete
    public abstract void delete(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Payment payment);
    
    @androidx.room.Query(value = "SELECT * FROM payments ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> getAllPayments();
    
    @androidx.room.Query(value = "SELECT * FROM payments WHERE invoiceId = :invoiceId ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> getPaymentsForInvoice(int invoiceId);
    
    @androidx.room.Query(value = "SELECT COALESCE(SUM(amount), 0.0) FROM payments WHERE date BETWEEN :startDate AND :endDate")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlyPaymentsSum(long startDate, long endDate);
    
    @androidx.room.Query(value = "SELECT * FROM payments WHERE invoiceId IN (SELECT invoiceId FROM invoices WHERE customerId = :customerId)")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> getPaymentsForCustomer(int customerId);
    
    @androidx.room.Query(value = "DELETE FROM payments WHERE invoiceId = :invoiceId")
    public abstract void deletePaymentsForInvoice(int invoiceId);
}