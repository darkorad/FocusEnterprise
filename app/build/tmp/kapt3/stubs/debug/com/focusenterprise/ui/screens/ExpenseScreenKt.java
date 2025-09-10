package com.focusenterprise.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.focusenterprise.data.Expense;
import com.focusenterprise.viewmodel.ExpenseViewModel;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\u001aH\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u001e\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0007\u001a,\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u000f\u001a\u00020\u0010\u00a8\u0006\u0016"}, d2 = {"ExpenseDialog", "", "expense", "Lcom/focusenterprise/data/Expense;", "onDismiss", "Lkotlin/Function0;", "onSave", "Lkotlin/Function1;", "onDelete", "ExpenseListItem", "onClick", "ExpenseScreen", "viewModel", "Lcom/focusenterprise/viewmodel/ExpenseViewModel;", "MonthSelector", "calendar", "Ljava/util/Calendar;", "onPrevious", "onNext", "getMonthDateRange", "Lkotlin/Pair;", "", "app_debug"})
public final class ExpenseScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ExpenseScreen(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.ExpenseViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void MonthSelector(@org.jetbrains.annotations.NotNull
    java.util.Calendar calendar, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrevious, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ExpenseListItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Expense expense, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ExpenseDialog(@org.jetbrains.annotations.Nullable
    com.focusenterprise.data.Expense expense, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.focusenterprise.data.Expense, kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.focusenterprise.data.Expense, kotlin.Unit> onDelete) {
    }
    
    @org.jetbrains.annotations.NotNull
    public static final kotlin.Pair<java.lang.Long, java.lang.Long> getMonthDateRange(@org.jetbrains.annotations.NotNull
    java.util.Calendar calendar) {
        return null;
    }
}