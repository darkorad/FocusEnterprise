package com.focusenterprise.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u0010"}, d2 = {"Lcom/focusenterprise/data/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "customerDao", "Lcom/focusenterprise/data/CustomerDao;", "expenseDao", "Lcom/focusenterprise/data/ExpenseDao;", "invoiceDao", "Lcom/focusenterprise/data/InvoiceDao;", "invoiceItemDao", "Lcom/focusenterprise/data/InvoiceItemDao;", "paymentDao", "Lcom/focusenterprise/data/PaymentDao;", "stockItemDao", "Lcom/focusenterprise/data/StockItemDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.focusenterprise.data.Customer.class, com.focusenterprise.data.StockItem.class, com.focusenterprise.data.Invoice.class, com.focusenterprise.data.InvoiceItem.class, com.focusenterprise.data.Payment.class, com.focusenterprise.data.Expense.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile
    @org.jetbrains.annotations.Nullable
    private static volatile com.focusenterprise.data.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull
    public static final com.focusenterprise.data.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.focusenterprise.data.CustomerDao customerDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.focusenterprise.data.StockItemDao stockItemDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.focusenterprise.data.InvoiceDao invoiceDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.focusenterprise.data.InvoiceItemDao invoiceItemDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.focusenterprise.data.PaymentDao paymentDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.focusenterprise.data.ExpenseDao expenseDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/focusenterprise/data/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/focusenterprise/data/AppDatabase;", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.focusenterprise.data.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}