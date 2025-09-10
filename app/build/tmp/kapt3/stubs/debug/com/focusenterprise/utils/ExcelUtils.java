package com.focusenterprise.utils;

import android.content.Context;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import com.focusenterprise.data.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/focusenterprise/utils/ExcelUtils;", "", "()V", "Companion", "app_debug"})
public final class ExcelUtils {
    @org.jetbrains.annotations.NotNull
    private static final java.text.SimpleDateFormat dateFormat = null;
    @org.jetbrains.annotations.NotNull
    private static final java.text.SimpleDateFormat monthYearFormat = null;
    @org.jetbrains.annotations.NotNull
    public static final com.focusenterprise.utils.ExcelUtils.Companion Companion = null;
    
    public ExcelUtils() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00bc\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0002]^B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J<\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0002JJ\u0010\u0015\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\u0002J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!JP\u0010\"\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020!2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\u0002J&\u0010$\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J&\u0010%\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J4\u0010&\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002JB\u0010\'\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J&\u0010(\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001e\u0010+\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u000bH\u0002J:\u0010,\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\\\u0010-\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bJT\u0010.\u001a\u00020\u001d2\u0006\u0010/\u001a\u0002002\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bJ2\u00101\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010 \u001a\u00020!JV\u00102\u001a\u00020\u001d2\u0006\u0010/\u001a\u0002002\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\u0002J\u0016\u00103\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u000e\u00104\u001a\u00020\u001d2\u0006\u0010/\u001a\u000200J\u0012\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108H\u0002J\u0012\u00109\u001a\u00020!2\b\u00107\u001a\u0004\u0018\u000108H\u0002J\u0010\u0010:\u001a\u00020!2\u0006\u0010;\u001a\u00020<H\u0002J\u0010\u0010=\u001a\u00020!2\u0006\u0010>\u001a\u00020!H\u0002J(\u0010?\u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0@2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J(\u0010A\u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000b0@2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000bH\u0002J(\u0010B\u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000b0@2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000bH\u0002J2\u0010C\u001a\u00020D2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010E\u001a\u00020F2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00120\u000bJ\u000e\u0010I\u001a\u00020D2\u0006\u0010J\u001a\u00020KJ\u0010\u0010L\u001a\u00020\u001d2\u0006\u0010M\u001a\u00020NH\u0002J\u0010\u0010O\u001a\u00020\u001d2\u0006\u0010P\u001a\u00020!H\u0002J\u0016\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010R\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J<\u0010S\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000b0T2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010E\u001a\u00020F2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bJ6\u0010U\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000b0T2\u0006\u0010\b\u001a\u00020\t2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\u0002J,\u0010V\u001a\u00020D2\u0006\u0010\b\u001a\u00020\t2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00120\u000bH\u0002J$\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\u0006\u0010\b\u001a\u00020\t2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00120\u000bH\u0002J\u0016\u0010X\u001a\b\u0012\u0004\u0012\u00020*0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010Y\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!H\u0002J \u0010Z\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!H\u0002J \u0010[\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!H\u0002J \u0010\\\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006_"}, d2 = {"Lcom/focusenterprise/utils/ExcelUtils$Companion;", "", "()V", "dateFormat", "Ljava/text/SimpleDateFormat;", "monthYearFormat", "addExpensesSection", "", "sheet", "Lorg/apache/poi/ss/usermodel/Sheet;", "expenses", "", "Lcom/focusenterprise/data/Expense;", "startRow", "headerStyle", "Lorg/apache/poi/ss/usermodel/CellStyle;", "addInvoicesSection", "invoices", "Lcom/focusenterprise/data/Invoice;", "customers", "Lcom/focusenterprise/data/Customer;", "addPaymentsSection", "payments", "Lcom/focusenterprise/data/Payment;", "createCustomersSheet", "", "workbook", "Lorg/apache/poi/xssf/usermodel/XSSFWorkbook;", "createInvoiceTemplate", "", "context", "Landroid/content/Context;", "fileName", "", "createMonthlyDataSheet", "monthName", "createSimpleCustomersSheet", "createSimpleExpensesSheet", "createSimpleInvoicesSheet", "createSimplePaymentsSheet", "createSimpleStockItemsSheet", "stockItems", "Lcom/focusenterprise/data/StockItem;", "createStockItemsSheet", "createSummarySheet", "exportAllDataByMonth", "exportAllDataToStream", "outputStream", "Ljava/io/OutputStream;", "exportInvoicesToExcel", "exportLargeDatasetToStream", "exportSampleTemplate", "exportSampleTemplateToStream", "getCellValueAsDouble", "", "cell", "Lorg/apache/poi/ss/usermodel/Cell;", "getCellValueAsString", "getMonthKey", "timestamp", "", "getMonthName", "monthKey", "groupExpensesByMonth", "", "groupInvoicesByMonth", "groupPaymentsByMonth", "importAllDataFromExcel", "Lcom/focusenterprise/utils/ExcelUtils$Companion$ImportResult;", "uri", "Landroid/net/Uri;", "existingCustomers", "existingInvoices", "importDataFromStream", "inputStream", "Ljava/io/InputStream;", "isRowEmpty", "row", "Lorg/apache/poi/ss/usermodel/Row;", "isValidEmail", "email", "parseCustomersFromSheet", "parseExpensesFromSheet", "parseInvoicesFromExcel", "Lkotlin/Pair;", "parseInvoicesFromSheet", "parseMonthlyDataSheet", "parsePaymentsFromSheet", "parseStockItemsFromSheet", "saveWithInternalStorage", "saveWithLegacyStorage", "saveWithMediaStore", "saveWorkbookToDownloads", "ImportResult", "ParseResult", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean exportAllDataByMonth(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.lang.String fileName, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Invoice> invoices, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Payment> payments, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Expense> expenses, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.StockItem> stockItems, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Customer> customers) {
            return false;
        }
        
        private final java.util.Map<java.lang.String, java.util.List<com.focusenterprise.data.Invoice>> groupInvoicesByMonth(java.util.List<com.focusenterprise.data.Invoice> invoices) {
            return null;
        }
        
        private final java.util.Map<java.lang.String, java.util.List<com.focusenterprise.data.Payment>> groupPaymentsByMonth(java.util.List<com.focusenterprise.data.Payment> payments) {
            return null;
        }
        
        private final java.util.Map<java.lang.String, java.util.List<com.focusenterprise.data.Expense>> groupExpensesByMonth(java.util.List<com.focusenterprise.data.Expense> expenses) {
            return null;
        }
        
        private final java.lang.String getMonthKey(long timestamp) {
            return null;
        }
        
        private final java.lang.String getMonthName(java.lang.String monthKey) {
            return null;
        }
        
        private final void createMonthlyDataSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.lang.String monthName, java.util.List<com.focusenterprise.data.Invoice> invoices, java.util.List<com.focusenterprise.data.Payment> payments, java.util.List<com.focusenterprise.data.Expense> expenses, java.util.List<com.focusenterprise.data.Customer> customers) {
        }
        
        private final int addInvoicesSection(org.apache.poi.ss.usermodel.Sheet sheet, java.util.List<com.focusenterprise.data.Invoice> invoices, java.util.List<com.focusenterprise.data.Customer> customers, int startRow, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
            return 0;
        }
        
        private final int addPaymentsSection(org.apache.poi.ss.usermodel.Sheet sheet, java.util.List<com.focusenterprise.data.Payment> payments, java.util.List<com.focusenterprise.data.Invoice> invoices, java.util.List<com.focusenterprise.data.Customer> customers, int startRow, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
            return 0;
        }
        
        private final int addExpensesSection(org.apache.poi.ss.usermodel.Sheet sheet, java.util.List<com.focusenterprise.data.Expense> expenses, int startRow, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
            return 0;
        }
        
        private final void createSummarySheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.Invoice> invoices, @kotlin.Suppress(names = {"UNUSED_PARAMETER"})
        java.util.List<com.focusenterprise.data.Payment> payments, java.util.List<com.focusenterprise.data.Expense> expenses) {
        }
        
        private final void createStockItemsSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.StockItem> stockItems) {
        }
        
        private final void createCustomersSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.Customer> customers) {
        }
        
        public final boolean createInvoiceTemplate(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.lang.String fileName) {
            return false;
        }
        
        public final boolean exportInvoicesToExcel(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Invoice> invoices, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Customer> customers, @org.jetbrains.annotations.NotNull
        java.lang.String fileName) {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final kotlin.Pair<java.util.List<com.focusenterprise.data.Invoice>, java.util.List<com.focusenterprise.data.Customer>> parseInvoicesFromExcel(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        android.net.Uri uri, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Customer> existingCustomers) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.focusenterprise.utils.ExcelUtils.Companion.ImportResult importAllDataFromExcel(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        android.net.Uri uri, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Customer> existingCustomers, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Invoice> existingInvoices) {
            return null;
        }
        
        private final kotlin.Pair<java.util.List<com.focusenterprise.data.Invoice>, java.util.List<com.focusenterprise.data.Customer>> parseInvoicesFromSheet(org.apache.poi.ss.usermodel.Sheet sheet, java.util.List<com.focusenterprise.data.Customer> existingCustomers) {
            return null;
        }
        
        private final java.util.List<com.focusenterprise.data.Payment> parsePaymentsFromSheet(org.apache.poi.ss.usermodel.Sheet sheet, @kotlin.Suppress(names = {"UNUSED_PARAMETER"})
        java.util.List<com.focusenterprise.data.Invoice> existingInvoices) {
            return null;
        }
        
        private final java.util.List<com.focusenterprise.data.Expense> parseExpensesFromSheet(org.apache.poi.ss.usermodel.Sheet sheet) {
            return null;
        }
        
        private final java.util.List<com.focusenterprise.data.StockItem> parseStockItemsFromSheet(org.apache.poi.ss.usermodel.Sheet sheet) {
            return null;
        }
        
        private final java.util.List<com.focusenterprise.data.Customer> parseCustomersFromSheet(org.apache.poi.ss.usermodel.Sheet sheet) {
            return null;
        }
        
        private final com.focusenterprise.utils.ExcelUtils.Companion.ImportResult parseMonthlyDataSheet(org.apache.poi.ss.usermodel.Sheet sheet, java.util.List<com.focusenterprise.data.Customer> existingCustomers, java.util.List<com.focusenterprise.data.Invoice> existingInvoices) {
            return null;
        }
        
        private final java.lang.String getCellValueAsString(org.apache.poi.ss.usermodel.Cell cell) {
            return null;
        }
        
        private final double getCellValueAsDouble(org.apache.poi.ss.usermodel.Cell cell) {
            return 0.0;
        }
        
        private final boolean isRowEmpty(org.apache.poi.ss.usermodel.Row row) {
            return false;
        }
        
        private final boolean isValidEmail(java.lang.String email) {
            return false;
        }
        
        private final boolean saveWorkbookToDownloads(android.content.Context context, org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.lang.String fileName) {
            return false;
        }
        
        private final boolean saveWithMediaStore(android.content.Context context, org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.lang.String fileName) {
            return false;
        }
        
        private final boolean saveWithLegacyStorage(android.content.Context context, org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.lang.String fileName) {
            return false;
        }
        
        private final boolean saveWithInternalStorage(android.content.Context context, org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.lang.String fileName) {
            return false;
        }
        
        private final boolean exportLargeDatasetToStream(java.io.OutputStream outputStream, java.util.List<com.focusenterprise.data.Invoice> invoices, java.util.List<com.focusenterprise.data.Payment> payments, java.util.List<com.focusenterprise.data.Expense> expenses, java.util.List<com.focusenterprise.data.StockItem> stockItems, java.util.List<com.focusenterprise.data.Customer> customers) {
            return false;
        }
        
        public final boolean exportAllDataToStream(@org.jetbrains.annotations.NotNull
        java.io.OutputStream outputStream, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Invoice> invoices, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Payment> payments, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Expense> expenses, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.StockItem> stockItems, @org.jetbrains.annotations.NotNull
        java.util.List<com.focusenterprise.data.Customer> customers) {
            return false;
        }
        
        private final void createSimpleCustomersSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.Customer> customers, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
        }
        
        private final void createSimpleInvoicesSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.Invoice> invoices, java.util.List<com.focusenterprise.data.Customer> customers, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
        }
        
        private final void createSimplePaymentsSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.Payment> payments, java.util.List<com.focusenterprise.data.Invoice> invoices, java.util.List<com.focusenterprise.data.Customer> customers, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
        }
        
        private final void createSimpleExpensesSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.Expense> expenses, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
        }
        
        private final void createSimpleStockItemsSheet(org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, java.util.List<com.focusenterprise.data.StockItem> stockItems, org.apache.poi.ss.usermodel.CellStyle headerStyle) {
        }
        
        public final boolean exportSampleTemplateToStream(@org.jetbrains.annotations.NotNull
        java.io.OutputStream outputStream) {
            return false;
        }
        
        public final boolean exportSampleTemplate(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.lang.String fileName) {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.focusenterprise.utils.ExcelUtils.Companion.ImportResult importDataFromStream(@org.jetbrains.annotations.NotNull
        java.io.InputStream inputStream) {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Ba\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u000fJ\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H\u00c6\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003Je\u0010 \u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020%H\u00d6\u0001J\t\u0010&\u001a\u00020\u000eH\u00d6\u0001R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006\'"}, d2 = {"Lcom/focusenterprise/utils/ExcelUtils$Companion$ImportResult;", "", "invoices", "", "Lcom/focusenterprise/data/Invoice;", "payments", "Lcom/focusenterprise/data/Payment;", "expenses", "Lcom/focusenterprise/data/Expense;", "stockItems", "Lcom/focusenterprise/data/StockItem;", "customers", "Lcom/focusenterprise/data/Customer;", "error", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;)V", "getCustomers", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "setError", "(Ljava/lang/String;)V", "getExpenses", "getInvoices", "getPayments", "getStockItems", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
        public static final class ImportResult {
            @org.jetbrains.annotations.NotNull
            private final java.util.List<com.focusenterprise.data.Invoice> invoices = null;
            @org.jetbrains.annotations.NotNull
            private final java.util.List<com.focusenterprise.data.Payment> payments = null;
            @org.jetbrains.annotations.NotNull
            private final java.util.List<com.focusenterprise.data.Expense> expenses = null;
            @org.jetbrains.annotations.NotNull
            private final java.util.List<com.focusenterprise.data.StockItem> stockItems = null;
            @org.jetbrains.annotations.NotNull
            private final java.util.List<com.focusenterprise.data.Customer> customers = null;
            @org.jetbrains.annotations.Nullable
            private java.lang.String error;
            
            public ImportResult(@org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Invoice> invoices, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Payment> payments, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Expense> expenses, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.StockItem> stockItems, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Customer> customers, @org.jetbrains.annotations.Nullable
            java.lang.String error) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Invoice> getInvoices() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Payment> getPayments() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Expense> getExpenses() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.StockItem> getStockItems() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Customer> getCustomers() {
                return null;
            }
            
            @org.jetbrains.annotations.Nullable
            public final java.lang.String getError() {
                return null;
            }
            
            public final void setError(@org.jetbrains.annotations.Nullable
            java.lang.String p0) {
            }
            
            public ImportResult() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Invoice> component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Payment> component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Expense> component3() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.StockItem> component4() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<com.focusenterprise.data.Customer> component5() {
                return null;
            }
            
            @org.jetbrains.annotations.Nullable
            public final java.lang.String component6() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.focusenterprise.utils.ExcelUtils.Companion.ImportResult copy(@org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Invoice> invoices, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Payment> payments, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Expense> expenses, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.StockItem> stockItems, @org.jetbrains.annotations.NotNull
            java.util.List<com.focusenterprise.data.Customer> customers, @org.jetbrains.annotations.Nullable
            java.lang.String error) {
                return null;
            }
            
            @java.lang.Override
            public boolean equals(@org.jetbrains.annotations.Nullable
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override
            @org.jetbrains.annotations.NotNull
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B!\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u00c6\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0004H\u00c6\u0003J/\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0004H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0014"}, d2 = {"Lcom/focusenterprise/utils/ExcelUtils$Companion$ParseResult;", "T", "", "data", "", "errors", "", "(Ljava/util/List;Ljava/util/List;)V", "getData", "()Ljava/util/List;", "getErrors", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
        static final class ParseResult<T extends java.lang.Object> {
            @org.jetbrains.annotations.NotNull
            private final java.util.List<T> data = null;
            @org.jetbrains.annotations.NotNull
            private final java.util.List<java.lang.String> errors = null;
            
            public ParseResult(@org.jetbrains.annotations.NotNull
            java.util.List<? extends T> data, @org.jetbrains.annotations.NotNull
            java.util.List<java.lang.String> errors) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<T> getData() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<java.lang.String> getErrors() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<T> component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.util.List<java.lang.String> component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.focusenterprise.utils.ExcelUtils.Companion.ParseResult<T> copy(@org.jetbrains.annotations.NotNull
            java.util.List<? extends T> data, @org.jetbrains.annotations.NotNull
            java.util.List<java.lang.String> errors) {
                return null;
            }
            
            @java.lang.Override
            public boolean equals(@org.jetbrains.annotations.Nullable
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override
            @org.jetbrains.annotations.NotNull
            public java.lang.String toString() {
                return null;
            }
        }
    }
}