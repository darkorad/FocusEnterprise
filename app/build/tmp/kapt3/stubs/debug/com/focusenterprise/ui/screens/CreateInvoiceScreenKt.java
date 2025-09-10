package com.focusenterprise.ui.screens;

import android.widget.Toast;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavController;
import com.focusenterprise.FocusEnterpriseApplication;
import com.focusenterprise.data.Customer;
import com.focusenterprise.data.InvoiceItem;
import com.focusenterprise.data.StockItem;
import com.focusenterprise.viewmodel.CreateInvoiceViewModel;
import com.focusenterprise.viewmodel.ViewModelFactory;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a>\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a4\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u001a&\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u00a8\u0006\u0019"}, d2 = {"AddItemDialog", "", "stockItems", "", "Lcom/focusenterprise/data/StockItem;", "onDismiss", "Lkotlin/Function0;", "onAddItem", "Lkotlin/Function2;", "", "CreateInvoiceScreen", "navController", "Landroidx/navigation/NavController;", "CustomerSelector", "customers", "Lcom/focusenterprise/data/Customer;", "selectedCustomer", "onCustomerSelected", "Lkotlin/Function1;", "LineItemRow", "item", "Lcom/focusenterprise/data/InvoiceItem;", "itemName", "", "onRemove", "app_debug"})
public final class CreateInvoiceScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void CreateInvoiceScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void CustomerSelector(@org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.Customer> customers, @org.jetbrains.annotations.Nullable
    com.focusenterprise.data.Customer selectedCustomer, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.focusenterprise.data.Customer, kotlin.Unit> onCustomerSelected) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void LineItemRow(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.InvoiceItem item, @org.jetbrains.annotations.NotNull
    java.lang.String itemName, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AddItemDialog(@org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.StockItem> stockItems, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super com.focusenterprise.data.StockItem, ? super java.lang.Integer, kotlin.Unit> onAddItem) {
    }
}