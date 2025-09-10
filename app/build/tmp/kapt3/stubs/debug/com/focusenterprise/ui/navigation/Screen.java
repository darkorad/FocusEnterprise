package com.focusenterprise.ui.navigation;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import com.focusenterprise.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\r\u000e\u000f\u0010\u0011\u0012\u0013B#\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u0082\u0001\u0007\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u00a8\u0006\u001b"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen;", "", "route", "", "resourceId", "", "icon", "(Ljava/lang/String;II)V", "getIcon", "()I", "getResourceId", "getRoute", "()Ljava/lang/String;", "Customers", "Dashboard", "DataManagement", "Expenses", "Invoices", "Reports", "Stock", "Lcom/focusenterprise/ui/navigation/Screen$Customers;", "Lcom/focusenterprise/ui/navigation/Screen$Dashboard;", "Lcom/focusenterprise/ui/navigation/Screen$DataManagement;", "Lcom/focusenterprise/ui/navigation/Screen$Expenses;", "Lcom/focusenterprise/ui/navigation/Screen$Invoices;", "Lcom/focusenterprise/ui/navigation/Screen$Reports;", "Lcom/focusenterprise/ui/navigation/Screen$Stock;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String route = null;
    private final int resourceId = 0;
    private final int icon = 0;
    
    private Screen(java.lang.String route, @androidx.annotation.StringRes
    int resourceId, @androidx.annotation.DrawableRes
    int icon) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoute() {
        return null;
    }
    
    public final int getResourceId() {
        return 0;
    }
    
    public final int getIcon() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen$Customers;", "Lcom/focusenterprise/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Customers extends com.focusenterprise.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.focusenterprise.ui.navigation.Screen.Customers INSTANCE = null;
        
        private Customers() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen$Dashboard;", "Lcom/focusenterprise/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Dashboard extends com.focusenterprise.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.focusenterprise.ui.navigation.Screen.Dashboard INSTANCE = null;
        
        private Dashboard() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen$DataManagement;", "Lcom/focusenterprise/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class DataManagement extends com.focusenterprise.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.focusenterprise.ui.navigation.Screen.DataManagement INSTANCE = null;
        
        private DataManagement() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen$Expenses;", "Lcom/focusenterprise/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Expenses extends com.focusenterprise.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.focusenterprise.ui.navigation.Screen.Expenses INSTANCE = null;
        
        private Expenses() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen$Invoices;", "Lcom/focusenterprise/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Invoices extends com.focusenterprise.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.focusenterprise.ui.navigation.Screen.Invoices INSTANCE = null;
        
        private Invoices() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen$Reports;", "Lcom/focusenterprise/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Reports extends com.focusenterprise.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.focusenterprise.ui.navigation.Screen.Reports INSTANCE = null;
        
        private Reports() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/focusenterprise/ui/navigation/Screen$Stock;", "Lcom/focusenterprise/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Stock extends com.focusenterprise.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.focusenterprise.ui.navigation.Screen.Stock INSTANCE = null;
        
        private Stock() {
        }
    }
}