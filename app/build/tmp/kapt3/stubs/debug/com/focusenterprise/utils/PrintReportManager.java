package com.focusenterprise.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import com.focusenterprise.viewmodel.SoldArticleSummary;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00032\u00020\u0001:\u0002\u0003\u0004B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0005"}, d2 = {"Lcom/focusenterprise/utils/PrintReportManager;", "", "()V", "Companion", "ReportPrintDocumentAdapter", "app_debug"})
public final class PrintReportManager {
    @org.jetbrains.annotations.NotNull
    public static final com.focusenterprise.utils.PrintReportManager.Companion Companion = null;
    
    public PrintReportManager() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a8\u0006\u000f"}, d2 = {"Lcom/focusenterprise/utils/PrintReportManager$Companion;", "", "()V", "printMonthlyReport", "", "context", "Landroid/content/Context;", "month", "Ljava/util/Calendar;", "earnings", "", "expenses", "soldArticles", "", "Lcom/focusenterprise/viewmodel/SoldArticleSummary;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void printMonthlyReport(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.util.Calendar month, double earnings, double expenses, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.viewmodel.SoldArticleSummary> soldArticles) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J6\u0010\u0016\u001a\u00020\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J;\u0010 \u001a\u00020\u00132\u0010\u0010!\u001a\f\u0012\u0006\b\u0001\u0012\u00020#\u0018\u00010\"2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020&H\u0016\u00a2\u0006\u0002\u0010\'R\u000e\u0010\r\u001a\u00020\u000eX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/focusenterprise/utils/PrintReportManager$ReportPrintDocumentAdapter;", "Landroid/print/PrintDocumentAdapter;", "context", "Landroid/content/Context;", "month", "Ljava/util/Calendar;", "earnings", "", "expenses", "soldArticles", "", "Lcom/focusenterprise/viewmodel/SoldArticleSummary;", "(Landroid/content/Context;Ljava/util/Calendar;DDLjava/util/List;)V", "LINE_HEIGHT", "", "MARGIN", "PAGE_HEIGHT", "PAGE_WIDTH", "drawReportContent", "", "canvas", "Landroid/graphics/Canvas;", "onLayout", "oldAttributes", "Landroid/print/PrintAttributes;", "newAttributes", "cancellationSignal", "Landroid/os/CancellationSignal;", "callback", "Landroid/print/PrintDocumentAdapter$LayoutResultCallback;", "extras", "Landroid/os/Bundle;", "onWrite", "pages", "", "Landroid/print/PageRange;", "destination", "Landroid/os/ParcelFileDescriptor;", "Landroid/print/PrintDocumentAdapter$WriteResultCallback;", "([Landroid/print/PageRange;Landroid/os/ParcelFileDescriptor;Landroid/os/CancellationSignal;Landroid/print/PrintDocumentAdapter$WriteResultCallback;)V", "app_debug"})
    static final class ReportPrintDocumentAdapter extends android.print.PrintDocumentAdapter {
        @org.jetbrains.annotations.NotNull
        private final android.content.Context context = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Calendar month = null;
        private final double earnings = 0.0;
        private final double expenses = 0.0;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.focusenterprise.viewmodel.SoldArticleSummary> soldArticles = null;
        private final int PAGE_WIDTH = 595;
        private final int PAGE_HEIGHT = 842;
        private final int MARGIN = 50;
        private final int LINE_HEIGHT = 20;
        
        public ReportPrintDocumentAdapter(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.util.Calendar month, double earnings, double expenses, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.viewmodel.SoldArticleSummary> soldArticles) {
            super();
        }
        
        @java.lang.Override
        public void onLayout(@org.jetbrains.annotations.Nullable
        android.print.PrintAttributes oldAttributes, @org.jetbrains.annotations.NotNull
        android.print.PrintAttributes newAttributes, @org.jetbrains.annotations.Nullable
        android.os.CancellationSignal cancellationSignal, @org.jetbrains.annotations.NotNull
        android.print.PrintDocumentAdapter.LayoutResultCallback callback, @org.jetbrains.annotations.Nullable
        android.os.Bundle extras) {
        }
        
        @java.lang.Override
        public void onWrite(@org.jetbrains.annotations.Nullable
        android.print.PageRange[] pages, @org.jetbrains.annotations.Nullable
        android.os.ParcelFileDescriptor destination, @org.jetbrains.annotations.Nullable
        android.os.CancellationSignal cancellationSignal, @org.jetbrains.annotations.NotNull
        android.print.PrintDocumentAdapter.WriteResultCallback callback) {
        }
        
        private final void drawReportContent(android.graphics.Canvas canvas) {
        }
    }
}