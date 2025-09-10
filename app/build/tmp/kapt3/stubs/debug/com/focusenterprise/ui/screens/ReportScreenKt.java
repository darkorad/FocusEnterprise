package com.focusenterprise.ui.screens;

import android.Manifest;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.focusenterprise.utils.PdfReportGenerator;
import com.focusenterprise.utils.PrintReportManager;
import com.focusenterprise.viewmodel.ReportViewModel;
import com.focusenterprise.viewmodel.SoldArticleSummary;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007\u001a$\u0010\u0005\u001a\u00020\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0010\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a \u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H\u0007\u001a\u0010\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u00a8\u0006\u0011"}, d2 = {"EarningsExpensesChart", "", "earnings", "", "expenses", "ReportActions", "onExportPdf", "Lkotlin/Function0;", "onPrint", "ReportScreen", "viewModel", "Lcom/focusenterprise/viewmodel/ReportViewModel;", "ReportSummary", "net", "SoldArticleItem", "article", "Lcom/focusenterprise/viewmodel/SoldArticleSummary;", "app_debug"})
public final class ReportScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ReportScreen(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.ReportViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ReportSummary(double earnings, double expenses, double net) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void EarningsExpensesChart(double earnings, double expenses) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ReportActions(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onExportPdf, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrint) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SoldArticleItem(@org.jetbrains.annotations.NotNull
    com.focusenterprise.viewmodel.SoldArticleSummary article) {
    }
}