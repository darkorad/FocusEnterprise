package com.focusenterprise.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavController;
import com.focusenterprise.data.Customer;
import com.focusenterprise.data.CustomerMonthlySummary;
import com.focusenterprise.viewmodel.CustomerViewModel;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001aH\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a,\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0018\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007\u001a\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002\u00a8\u0006\u0017"}, d2 = {"CustomerDialog", "", "customer", "Lcom/focusenterprise/data/Customer;", "onDismiss", "Lkotlin/Function0;", "onSave", "Lkotlin/Function1;", "onDelete", "CustomerListItem", "onEditClick", "onDetailClick", "CustomerMonthlySummaryItem", "summary", "Lcom/focusenterprise/data/CustomerMonthlySummary;", "CustomerScreen", "viewModel", "Lcom/focusenterprise/viewmodel/CustomerViewModel;", "navController", "Landroidx/navigation/NavController;", "formatMonthYear", "", "monthYear", "app_debug"})
public final class CustomerScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void CustomerScreen(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.CustomerViewModel viewModel, @org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CustomerMonthlySummaryItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.CustomerMonthlySummary summary, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDetailClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CustomerListItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Customer customer, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEditClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDetailClick) {
    }
    
    private static final java.lang.String formatMonthYear(java.lang.String monthYear) {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void CustomerDialog(@org.jetbrains.annotations.Nullable
    com.focusenterprise.data.Customer customer, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.focusenterprise.data.Customer, kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.focusenterprise.data.Customer, kotlin.Unit> onDelete) {
    }
}