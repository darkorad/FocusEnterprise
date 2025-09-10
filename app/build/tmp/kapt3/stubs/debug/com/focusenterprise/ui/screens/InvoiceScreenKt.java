package com.focusenterprise.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavController;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.runtime.*;
import android.content.Context;
import com.focusenterprise.utils.ExcelUtils;
import com.focusenterprise.utils.PermissionHelper;
import com.focusenterprise.data.Invoice;
import com.focusenterprise.viewmodel.CustomerViewModel;
import com.focusenterprise.viewmodel.InvoiceViewModel;
import com.focusenterprise.viewmodel.PaymentViewModel;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a2\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u001e\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007\u001a\u0018\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0007\u001a,\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a<\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a(\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007\u001a\\\u0010\u001f\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\"\u001a\u00020\u0015H\u0007\u001a\u0018\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0015H\u0002\u001aI\u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020)2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u000b2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0007H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010+\u001a-\u0010,\u001a\u00020\u00012\u0006\u0010(\u001a\u00020)2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0007H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010-\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2 = {"AddPaymentDialog", "", "invoice", "Lcom/focusenterprise/data/Invoice;", "onDismiss", "Lkotlin/Function0;", "onSave", "Lkotlin/Function1;", "", "DataExportTab", "invoices", "", "customerViewModel", "Lcom/focusenterprise/viewmodel/CustomerViewModel;", "DataImportTab", "invoiceViewModel", "Lcom/focusenterprise/viewmodel/InvoiceViewModel;", "DeleteInvoiceDialog", "onConfirm", "InvoiceListItem", "customerName", "", "showPaymentButton", "", "onAddPaymentClick", "onDeleteClick", "InvoiceScreen", "paymentViewModel", "Lcom/focusenterprise/viewmodel/PaymentViewModel;", "navController", "Landroidx/navigation/NavController;", "InvoiceTabContent", "customers", "Lcom/focusenterprise/data/Customer;", "emptyMessage", "formatDate", "timestamp", "", "format", "performExport", "context", "Landroid/content/Context;", "onResult", "(Landroid/content/Context;Ljava/util/List;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "performTemplateDownload", "(Landroid/content/Context;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class InvoiceScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void InvoiceScreen(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.InvoiceViewModel invoiceViewModel, @org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.CustomerViewModel customerViewModel, @org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.PaymentViewModel paymentViewModel, @org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void InvoiceTabContent(@org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.Invoice> invoices, @org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.Customer> customers, boolean showPaymentButton, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.focusenterprise.data.Invoice, kotlin.Unit> onAddPaymentClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.focusenterprise.data.Invoice, kotlin.Unit> onDeleteClick, @org.jetbrains.annotations.NotNull
    java.lang.String emptyMessage) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void InvoiceListItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    java.lang.String customerName, boolean showPaymentButton, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddPaymentClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AddPaymentDialog(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Double, kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DeleteInvoiceDialog(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DataImportTab(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.InvoiceViewModel invoiceViewModel, @org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.CustomerViewModel customerViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DataExportTab(@org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.Invoice> invoices, @org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.CustomerViewModel customerViewModel) {
    }
    
    private static final java.lang.String formatDate(long timestamp, java.lang.String format) {
        return null;
    }
    
    private static final java.lang.Object performExport(android.content.Context context, java.util.List<com.focusenterprise.data.Invoice> invoices, java.util.List<com.focusenterprise.data.Customer> customers, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private static final java.lang.Object performTemplateDownload(android.content.Context context, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}