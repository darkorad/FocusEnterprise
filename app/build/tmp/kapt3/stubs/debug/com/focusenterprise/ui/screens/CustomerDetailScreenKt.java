package com.focusenterprise.ui.screens;

import android.widget.Toast;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import com.focusenterprise.data.Invoice;
import com.focusenterprise.data.Payment;
import com.focusenterprise.viewmodel.CustomerDetailViewModel;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u001e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\u0010\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u00a8\u0006\u0010"}, d2 = {"CustomerDetailScreen", "", "viewModel", "Lcom/focusenterprise/viewmodel/CustomerDetailViewModel;", "InvoiceHistoryItem", "invoice", "Lcom/focusenterprise/data/Invoice;", "onExport", "Lkotlin/Function0;", "PaymentHistoryItem", "payment", "Lcom/focusenterprise/data/Payment;", "formatDate", "", "timestamp", "", "app_debug"})
public final class CustomerDetailScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void CustomerDetailScreen(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.CustomerDetailViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void InvoiceHistoryItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onExport) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PaymentHistoryItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Payment payment) {
    }
    
    private static final java.lang.String formatDate(long timestamp) {
        return null;
    }
}