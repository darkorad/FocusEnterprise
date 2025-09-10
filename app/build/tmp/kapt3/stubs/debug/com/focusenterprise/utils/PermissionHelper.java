package com.focusenterprise.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import androidx.core.content.ContextCompat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/focusenterprise/utils/PermissionHelper;", "", "()V", "Companion", "app_debug"})
public final class PermissionHelper {
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1001;
    public static final int MANAGE_STORAGE_REQUEST_CODE = 1002;
    @org.jetbrains.annotations.NotNull
    public static final com.focusenterprise.utils.PermissionHelper.Companion Companion = null;
    
    public PermissionHelper() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007J\u0011\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/focusenterprise/utils/PermissionHelper$Companion;", "", "()V", "MANAGE_STORAGE_REQUEST_CODE", "", "STORAGE_PERMISSION_REQUEST_CODE", "getPermissionExplanation", "", "getRequiredStoragePermissions", "", "()[Ljava/lang/String;", "hasRegularStoragePermissions", "", "context", "Landroid/content/Context;", "hasStoragePermissions", "requestManageExternalStoragePermission", "", "shouldShowRationale", "activity", "Landroidx/activity/ComponentActivity;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String[] getRequiredStoragePermissions() {
            return null;
        }
        
        public final boolean hasStoragePermissions(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return false;
        }
        
        private final boolean hasRegularStoragePermissions(android.content.Context context) {
            return false;
        }
        
        public final boolean shouldShowRationale(@org.jetbrains.annotations.NotNull
        androidx.activity.ComponentActivity activity) {
            return false;
        }
        
        public final void requestManageExternalStoragePermission(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPermissionExplanation() {
            return null;
        }
    }
}