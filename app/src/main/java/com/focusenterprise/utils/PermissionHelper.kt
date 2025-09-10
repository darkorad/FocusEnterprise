package com.focusenterprise.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.content.ContextCompat

class PermissionHelper {
    companion object {
        const val STORAGE_PERMISSION_REQUEST_CODE = 1001
        const val MANAGE_STORAGE_REQUEST_CODE = 1002
        
        fun getRequiredStoragePermissions(): Array<String> {
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                    // Android 13+ (API 33+) - Use granular media permissions
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_AUDIO
                    )
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    // Android 11-12 (API 30-32) - Use READ_EXTERNAL_STORAGE
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                else -> {
                    // Android 10 and below (API 29-) - Need legacy storage permissions
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
            }
        }
        
        fun hasStoragePermissions(context: Context): Boolean {
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    // For Android 11+, check if we have MANAGE_EXTERNAL_STORAGE or regular permissions
                    Environment.isExternalStorageManager() || hasRegularStoragePermissions(context)
                }
                else -> {
                    hasRegularStoragePermissions(context)
                }
            }
        }
        
        private fun hasRegularStoragePermissions(context: Context): Boolean {
            val requiredPermissions = getRequiredStoragePermissions()
            
            return requiredPermissions.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        
        fun shouldShowRationale(activity: androidx.activity.ComponentActivity): Boolean {
            val requiredPermissions = getRequiredStoragePermissions()
            
            return requiredPermissions.any { permission ->
                activity.shouldShowRequestPermissionRationale(permission)
            }
        }
        
        fun requestManageExternalStoragePermission(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:${context.packageName}")
                    context.startActivity(intent)
                } catch (e: Exception) {
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    context.startActivity(intent)
                }
            }
        }
        
        fun getPermissionExplanation(): String {
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    "This app needs storage access to export and import Excel files. Please grant 'All files access' permission for the best experience, or allow the requested permissions."
                }
                else -> {
                    "This app needs storage access to export and import Excel files. Please allow the requested permissions."
                }
            }
        }
    }
}