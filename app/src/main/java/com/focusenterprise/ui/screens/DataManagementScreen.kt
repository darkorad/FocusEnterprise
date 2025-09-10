package com.focusenterprise.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.focusenterprise.FocusEnterpriseApplication
import com.focusenterprise.viewmodel.DataManagementViewModel
import com.focusenterprise.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataManagementScreen(
    viewModel: DataManagementViewModel = viewModel(factory = ViewModelFactory(LocalContext.current.applicationContext as FocusEnterpriseApplication))
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    // File picker for import
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.importDataFromExcel(context, it) }
    }
    
    // File picker for export location
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    ) { uri: Uri? ->
        uri?.let { viewModel.exportDataToExcel(context, it) }
    }
    
    // File picker for template export
    val templateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    ) { uri: Uri? ->
        uri?.let { viewModel.exportSampleTemplate(context, it) }
    }
    
    // File picker for sales export
    val salesExportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    ) { uri: Uri? ->
        uri?.let { viewModel.exportSalesDataToExcel(context, it) }
    }
    
    // File picker for sales import
    val salesImportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.importSalesDataFromExcel(context, it) }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Data Management",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Export your data to Excel or import data from Excel files",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Export Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDownload,
                        contentDescription = "Export",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Export Data",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Text(
                    text = "Export all your business data (customers, invoices, payments, expenses, stock) to an Excel file",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val fileName = "FocusEnterprise_Data_${System.currentTimeMillis()}.xlsx"
                            exportLauncher.launch(fileName)
                        },
                        enabled = !viewModel.isExporting.collectAsState().value,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (viewModel.isExporting.collectAsState().value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(if (viewModel.isExporting.collectAsState().value) "Exporting..." else "Export Data")
                    }
                    
                    OutlinedButton(
                        onClick = {
                            val fileName = "FocusEnterprise_Template_${System.currentTimeMillis()}.xlsx"
                            templateLauncher.launch(fileName)
                        },
                        enabled = !viewModel.isExporting.collectAsState().value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Template",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Template")
                    }
                }
            }
        }
        
        // Import Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = "Import",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Import Data",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Text(
                    text = "Import data from an Excel file to update your database. Make sure the file follows the correct format.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Button(
                    onClick = {
                        importLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    },
                    enabled = !viewModel.isImporting.collectAsState().value,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (viewModel.isImporting.collectAsState().value) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(if (viewModel.isImporting.collectAsState().value) "Importing..." else "Select Excel File")
                }
            }
        }
        
        // Status Messages
        val statusMessage by viewModel.statusMessage.collectAsState()
        val isError by viewModel.isError.collectAsState()
        
        if (statusMessage.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isError) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = if (isError) "Error" else "Success",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isError) {
                            MaterialTheme.colorScheme.onErrorContainer
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }
                    )
                    
                    Text(
                        text = statusMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isError) {
                            MaterialTheme.colorScheme.onErrorContainer
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    TextButton(
                        onClick = { viewModel.clearStatus() }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        }
        
        // Sales Data Export/Import Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Assessment,
                        contentDescription = "Sales Data",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Sales Data Export/Import",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Text(
                    text = "Export sales data with proper column headers or import sales data from Excel files",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // Sales Export Section
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Export Sales Data",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    
                    val isSalesExporting by viewModel.isSalesExporting.collectAsState()
                    val salesExportProgress by viewModel.salesExportProgress.collectAsState()
                    
                    Button(
                        onClick = {
                            val fileName = viewModel.generateSalesExportFileName()
                            salesExportLauncher.launch(fileName)
                        },
                        enabled = !isSalesExporting,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isSalesExporting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(if (isSalesExporting) "Exporting Sales Data..." else "Export Sales Data")
                    }
                    
                    // Sales Export Progress Bar
                    if (isSalesExporting && salesExportProgress > 0) {
                        Column {
                            LinearProgressIndicator(
                                progress = salesExportProgress / 100f,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "Progress: $salesExportProgress%",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
                
                Divider()
                
                // Sales Import Section
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Import Sales Data",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    
                    val isSalesImporting by viewModel.isSalesImporting.collectAsState()
                    val salesImportProgress by viewModel.salesImportProgress.collectAsState()
                    
                    OutlinedButton(
                        onClick = {
                            salesImportLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                        },
                        enabled = !isSalesImporting,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.FileUpload,
                            contentDescription = "Import Sales",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if (isSalesImporting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(if (isSalesImporting) "Importing Sales Data..." else "Select Sales Excel File")
                    }
                    
                    // Sales Import Progress Bar
                    if (isSalesImporting && salesImportProgress > 0) {
                        Column {
                            LinearProgressIndicator(
                                progress = salesImportProgress / 100f,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "Progress: $salesImportProgress%",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
        
        // Import Summary
        val importSummary by viewModel.importSummary.collectAsState()
        if (importSummary.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Import Summary",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = importSummary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        
        // Sales Import Summary
        val salesImportSummary by viewModel.salesImportSummary.collectAsState()
        if (salesImportSummary.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Sales Import Summary",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = salesImportSummary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}