package com.focusenterprise.data.repositories;

import com.focusenterprise.data.Payment;
import com.focusenterprise.data.PaymentDao;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eJ\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0014\u001a\u00020\u0012J\u0019\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/focusenterprise/data/repositories/PaymentRepository;", "", "paymentDao", "Lcom/focusenterprise/data/PaymentDao;", "(Lcom/focusenterprise/data/PaymentDao;)V", "allPayments", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/focusenterprise/data/Payment;", "getAllPayments", "()Lkotlinx/coroutines/flow/Flow;", "getMonthlyPaymentsSum", "", "startDate", "", "endDate", "getPaymentsForCustomer", "customerId", "", "getPaymentsForInvoice", "invoiceId", "insert", "", "payment", "(Lcom/focusenterprise/data/Payment;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PaymentRepository {
    @org.jetbrains.annotations.NotNull
    private final com.focusenterprise.data.PaymentDao paymentDao = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> allPayments = null;
    
    public PaymentRepository(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.PaymentDao paymentDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> getAllPayments() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> getPaymentsForInvoice(int invoiceId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Payment payment, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Double> getMonthlyPaymentsSum(long startDate, long endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.Payment>> getPaymentsForCustomer(int customerId) {
        return null;
    }
}