package com.focusenterprise.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import com.focusenterprise.viewmodel.DashboardViewModel;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0006\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u0010\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0007\u001a\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a\u0010\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\u0016\u0010\u0010\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0007\u00a8\u0006\u0014"}, d2 = {"ActivityItem", "", "activity", "Lcom/focusenterprise/ui/screens/Activity;", "AlertsSection", "lowStockItems", "", "DashboardScreen", "viewModel", "Lcom/focusenterprise/viewmodel/DashboardViewModel;", "MetricCardItem", "metric", "Lcom/focusenterprise/ui/screens/MetricCard;", "QuickActionItem", "action", "Lcom/focusenterprise/ui/screens/QuickAction;", "SalesChart", "salesData", "", "", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.DashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void MetricCardItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.ui.screens.MetricCard metric) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void QuickActionItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.ui.screens.QuickAction action) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SalesChart(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Double> salesData) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AlertsSection(int lowStockItems) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ActivityItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.ui.screens.Activity activity) {
    }
}