package com.focusenterprise.data;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Data class representing a sales record for export/import operations.
 * Combines data from Invoice, InvoiceItem, Customer, and StockItem entities.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\b\u0018\u0000 %2\u00020\u0001:\u0001%B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\nH\u00c6\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010!\u001a\u00020\u0005J\t\u0010\"\u001a\u00020\bH\u00d6\u0001J\u0006\u0010#\u001a\u00020\u001fJ\t\u0010$\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012\u00a8\u0006&"}, d2 = {"Lcom/focusenterprise/data/SalesRecord;", "", "date", "", "customerName", "", "product", "quantity", "", "price", "", "total", "(JLjava/lang/String;Ljava/lang/String;IDD)V", "getCustomerName", "()Ljava/lang/String;", "getDate", "()J", "getPrice", "()D", "getProduct", "getQuantity", "()I", "getTotal", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "getFormattedDate", "hashCode", "isValid", "toString", "Companion", "app_debug"})
public final class SalesRecord {
    private final long date = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String customerName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String product = null;
    private final int quantity = 0;
    private final double price = 0.0;
    private final double total = 0.0;
    
    /**
     * Expected column headers for Excel export/import
     */
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String[] COLUMN_HEADERS = {"Date", "CustomerName", "Product", "Quantity", "Price", "Total"};
    @org.jetbrains.annotations.NotNull
    public static final com.focusenterprise.data.SalesRecord.Companion Companion = null;
    
    public SalesRecord(long date, @org.jetbrains.annotations.NotNull
    java.lang.String customerName, @org.jetbrains.annotations.NotNull
    java.lang.String product, int quantity, double price, double total) {
        super();
    }
    
    public final long getDate() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCustomerName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProduct() {
        return null;
    }
    
    public final int getQuantity() {
        return 0;
    }
    
    public final double getPrice() {
        return 0.0;
    }
    
    public final double getTotal() {
        return 0.0;
    }
    
    /**
     * Formats the date as a readable string for Excel export
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFormattedDate() {
        return null;
    }
    
    /**
     * Validates that all required fields have valid values
     */
    public final boolean isValid() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.focusenterprise.data.SalesRecord copy(long date, @org.jetbrains.annotations.NotNull
    java.lang.String customerName, @org.jetbrains.annotations.NotNull
    java.lang.String product, int quantity, double price, double total) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J8\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u0012\u0010\u0017\u001a\u00020\u00052\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0005H\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u001b"}, d2 = {"Lcom/focusenterprise/data/SalesRecord$Companion;", "", "()V", "COLUMN_HEADERS", "", "", "getCOLUMN_HEADERS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "fromExcelRow", "Lcom/focusenterprise/data/SalesRecord;", "date", "customerName", "product", "quantity", "price", "total", "row", "Lorg/apache/poi/ss/usermodel/Row;", "getCellNumericValue", "", "cell", "Lorg/apache/poi/ss/usermodel/Cell;", "getCellStringValue", "parseDate", "", "dateString", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Expected column headers for Excel export/import
         */
        @org.jetbrains.annotations.NotNull
        public final java.lang.String[] getCOLUMN_HEADERS() {
            return null;
        }
        
        /**
         * Creates a SalesRecord from Excel row data
         */
        @org.jetbrains.annotations.Nullable
        public final com.focusenterprise.data.SalesRecord fromExcelRow(@org.jetbrains.annotations.NotNull
        java.lang.String date, @org.jetbrains.annotations.NotNull
        java.lang.String customerName, @org.jetbrains.annotations.NotNull
        java.lang.String product, @org.jetbrains.annotations.NotNull
        java.lang.String quantity, @org.jetbrains.annotations.NotNull
        java.lang.String price, @org.jetbrains.annotations.NotNull
        java.lang.String total) {
            return null;
        }
        
        /**
         * Parse SalesRecord from Excel row
         * Used during import operations
         */
        @org.jetbrains.annotations.NotNull
        public final com.focusenterprise.data.SalesRecord fromExcelRow(@org.jetbrains.annotations.NotNull
        org.apache.poi.ss.usermodel.Row row) {
            return null;
        }
        
        private final java.lang.String getCellStringValue(org.apache.poi.ss.usermodel.Cell cell) {
            return null;
        }
        
        private final double getCellNumericValue(org.apache.poi.ss.usermodel.Cell cell) {
            return 0.0;
        }
        
        private final long parseDate(java.lang.String dateString) {
            return 0L;
        }
    }
}