package com.focusenterprise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import com.focusenterprise.utils.ExcelUtils
import com.focusenterprise.utils.PermissionHelper
import kotlinx.coroutines.launch
import com.focusenterprise.data.Invoice
import com.focusenterprise.viewmodel.CustomerViewModel
import com.focusenterprise.viewmodel.InvoiceViewModel
import com.focusenterprise.viewmodel.PaymentViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceScreen(
    invoiceViewModel: InvoiceViewModel,
    customerViewModel: CustomerViewModel,
    paymentViewModel: PaymentViewModel,
    navController: NavController
) {
    val activeInvoices by invoiceViewModel.activeInvoices.collectAsState()
    val completedInvoices by invoiceViewModel.completedInvoices.collectAsState()
    val customers by customerViewModel.allCustomers.collectAsState()
    var showPaymentDialog by remember { mutableStateOf<Invoice?>(null) }
    var showDeleteDialog by remember { mutableStateOf<Invoice?>(null) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Invoice Management",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                        Text(
                            text = "Manage invoices, import/export data",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                    }
                    FloatingActionButton(
                        onClick = { navController.navigate("create_invoice") },
                        containerColor = Color(0xFF4F46E5),
                        contentColor = Color.White,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Create Invoice",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Tab Row
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.White,
                    contentColor = Color(0xFF4F46E5),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Color(0xFF4F46E5)
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = {
                            Text(
                                "Active",
                                color = if (selectedTabIndex == 0) Color(0xFF4F46E5) else Color(0xFF6B7280)
                            )
                        }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = {
                            Text(
                                "Completed",
                                color = if (selectedTabIndex == 1) Color(0xFF4F46E5) else Color(0xFF6B7280)
                            )
                        }
                    )
                    Tab(
                        selected = selectedTabIndex == 2,
                        onClick = { selectedTabIndex = 2 },
                        text = {
                            Text(
                                "Data Import",
                                color = if (selectedTabIndex == 2) Color(0xFF4F46E5) else Color(0xFF6B7280)
                            )
                        }
                    )
                    Tab(
                        selected = selectedTabIndex == 3,
                        onClick = { selectedTabIndex = 3 },
                        text = {
                            Text(
                                "Data Export",
                                color = if (selectedTabIndex == 3) Color(0xFF4F46E5) else Color(0xFF6B7280)
                            )
                        }
                    )
                }
            }
        }

        // Content
        when (selectedTabIndex) {
            0 -> InvoiceTabContent(
                invoices = activeInvoices,
                customers = customers,
                showPaymentButton = true,
                onAddPaymentClick = { showPaymentDialog = it },
                onDeleteClick = { showDeleteDialog = it },
                emptyMessage = "No unpaid invoices at the moment."
            )
            1 -> InvoiceTabContent(
                invoices = completedInvoices,
                customers = customers,
                showPaymentButton = false,
                onAddPaymentClick = { },
                onDeleteClick = { showDeleteDialog = it },
                emptyMessage = "No completed invoices yet."
            )
            2 -> DataImportTab(
                invoiceViewModel = invoiceViewModel,
                customerViewModel = customerViewModel
            )
            3 -> DataExportTab(
                invoices = activeInvoices + completedInvoices,
                customerViewModel = customerViewModel
            )
        }
    }

    showPaymentDialog?.let { invoice ->
        AddPaymentDialog(
            invoice = invoice,
            onDismiss = { showPaymentDialog = null },
            onSave = { amount ->
                paymentViewModel.addPayment(invoice, amount)
                showPaymentDialog = null
            }
        )
    }

    showDeleteDialog?.let { invoice ->
        DeleteInvoiceDialog(
            invoice = invoice,
            onDismiss = { showDeleteDialog = null },
            onConfirm = {
                invoiceViewModel.deleteInvoice(invoice)
                showDeleteDialog = null
            })
    }
    

}

@Composable
fun InvoiceTabContent(
    invoices: List<Invoice>,
    customers: List<com.focusenterprise.data.Customer>,
    showPaymentButton: Boolean,
    onAddPaymentClick: (Invoice) -> Unit,
    onDeleteClick: (Invoice) -> Unit,
    emptyMessage: String
) {
    if (invoices.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Filled.Description,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color(0xFFD1D5DB)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "All caught up!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Text(
                    text = emptyMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(invoices) { invoice ->
                val customerName = customers.find { it.customerId == invoice.customerId }?.name ?: "N/A"
                InvoiceListItem(
                    invoice = invoice,
                    customerName = customerName,
                    showPaymentButton = showPaymentButton,
                    onAddPaymentClick = { onAddPaymentClick(invoice) },
                    onDeleteClick = { onDeleteClick(invoice) }
                )
            }
        }
    }
}

@Composable
fun InvoiceListItem(
    invoice: Invoice,
    customerName: String,
    showPaymentButton: Boolean,
    onAddPaymentClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Invoice #${invoice.invoiceId}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF111827)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "To: $customerName",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        text = formatDate(invoice.date, "dd MMM yyyy"),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9CA3AF)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${"%.0f".format(invoice.totalAmount)} RSD",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF111827)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = when (invoice.status) {
                            "paid" -> Color(0xFF10B981)
                            "partial" -> Color(0xFFF59E0B)
                            else -> Color(0xFFEF4444)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = invoice.status.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (showPaymentButton && invoice.status != "paid") {
                    Button(
                        onClick = onAddPaymentClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF10B981)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Payment")
                    }
                }
                OutlinedButton(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFEF4444)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(if (showPaymentButton && invoice.status != "paid") 1f else 2f)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentDialog(invoice: Invoice, onDismiss: () -> Unit, onSave: (Double) -> Unit) {
    var amount by remember { mutableStateOf("") }
    val remaining = invoice.totalAmount - invoice.paidAmount

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Record Payment",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF111827)
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Invoice #${invoice.invoiceId}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Remaining amount: ${"%.0f".format(remaining)} RSD",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF111827)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Payment Amount (RSD)") },
                    placeholder = { Text("Enter payment amount") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4F46E5),
                        focusedLabelColor = Color(0xFF4F46E5)
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val paymentAmount = amount.toDoubleOrNull()
                    if (paymentAmount != null && paymentAmount > 0) {
                        onSave(paymentAmount)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4F46E5)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Record Payment")
            }
        },
        dismissButton = {
            OutlinedButton(
                 onClick = onDismiss,
                 shape = RoundedCornerShape(8.dp)
             ) {
                 Text(
                     "Cancel",
                     color = Color(0xFF6B7280)
                 )
             }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun DeleteInvoiceDialog(
    invoice: Invoice,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Delete Invoice",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF111827)
            )
        },
        text = {
            Text(
                "Are you sure you want to delete Invoice #${invoice.invoiceId}? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Cancel",
                    color = Color(0xFF6B7280)
                )
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun DataImportTab(
    invoiceViewModel: InvoiceViewModel,
    customerViewModel: CustomerViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isImporting by remember { mutableStateOf(false) }
    var importMessage by remember { mutableStateOf("") }
    var isDownloadingTemplate by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showManageStorageDialog by remember { mutableStateOf(false) }
    
    // Permission launcher for template download
    val templatePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted || PermissionHelper.hasStoragePermissions(context)) {
            // Permissions granted, proceed with template download
            scope.launch {
                performTemplateDownload(context) { message ->
                    importMessage = message
                    isDownloadingTemplate = false
                }
            }
        } else {
            // Check if we should show manage storage dialog for Android 11+
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                showManageStorageDialog = true
            } else {
                importMessage = "Storage permission is required to download template. Please grant the permission and try again."
            }
            isDownloadingTemplate = false
        }
    }
    
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            scope.launch {
                isImporting = true
                try {
                    val existingCustomers = customerViewModel.allCustomers.value
                    val (invoices, newCustomers) = ExcelUtils.parseInvoicesFromExcel(
                        context, uri, existingCustomers
                    )
                    
                    // Insert new customers first
                    newCustomers.forEach { customer ->
                        customerViewModel.insertCustomer(customer)
                    }
                    
                    // Insert invoices
                    invoices.forEach { invoice ->
                        invoiceViewModel.insertInvoiceWithItems(invoice, emptyList())
                    }
                    
                    importMessage = "Successfully imported ${invoices.size} invoices and ${newCustomers.size} new customers"
                } catch (e: Exception) {
                    importMessage = "Import failed: ${e.message}"
                } finally {
                    isImporting = false
                }
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.FileUpload,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF4F46E5)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Import Historical Data",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Upload Excel files to import historical invoice data",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B7280),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        
        if (importMessage.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (importMessage.contains("Successfully")) 
                        Color(0xFFD4EDDA) else Color(0xFFF8D7DA)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = importMessage,
                    modifier = Modifier.padding(16.dp),
                    color = if (importMessage.contains("Successfully")) 
                        Color(0xFF155724) else Color(0xFF721C24),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Button(
            onClick = {
                isDownloadingTemplate = true
                importMessage = ""
                
                if (PermissionHelper.hasStoragePermissions(context)) {
                    // Permissions already granted, proceed with template download
                    scope.launch {
                        performTemplateDownload(context) { message ->
                            importMessage = message
                            isDownloadingTemplate = false
                        }
                    }
                } else {
                    // Request permissions using the launcher
                    val requiredPermissions = PermissionHelper.getRequiredStoragePermissions()
                    if (requiredPermissions.isNotEmpty()) {
                        templatePermissionLauncher.launch(requiredPermissions)
                    } else {
                        showPermissionDialog = true
                    }
                    isDownloadingTemplate = false
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF10B981)
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = !isDownloadingTemplate,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            if (isDownloadingTemplate) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Icon(
                    Icons.Filled.FileDownload,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isDownloadingTemplate) "Downloading..." else "Download Template")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { filePickerLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") },
            enabled = !isImporting,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4F46E5)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            if (isImporting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Icon(
                    Icons.Filled.FileUpload,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isImporting) "Importing..." else "Import Excel File")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Import Requirements:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Excel file (.xlsx format)\n• Required columns: Customer Name, Customer PIB, Customer Phone, Customer Email, Customer Address, Invoice Date, Total Amount, Paid Amount, Payment Status\n• Date format: yyyy-MM-dd (e.g., 2024-01-15)\n• Amount in RSD (numbers only, no currency symbols)\n• Status: pending, partial, or paid\n• Email must be valid format if provided\n• Paid amount cannot exceed total amount",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280)
                )
            }
        }
    }
    
    // Permission rationale dialog
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = {
                Text(
                    text = "Storage Permission Required",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(
                    text = PermissionHelper.getPermissionExplanation(),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDialog = false
                        val requiredPermissions = PermissionHelper.getRequiredStoragePermissions()
                        if (requiredPermissions.isNotEmpty()) {
                            templatePermissionLauncher.launch(requiredPermissions)
                        }
                    }
                ) {
                    Text("Grant Permission")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showPermissionDialog = false
                        isDownloadingTemplate = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Manage external storage dialog for Android 11+
    if (showManageStorageDialog) {
        AlertDialog(
            onDismissRequest = { 
                showManageStorageDialog = false
                isDownloadingTemplate = false
            },
            title = {
                Text(
                    text = "Storage Access Required",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(
                    text = if (isDownloadingTemplate) {
                        "This app needs access to manage files on your device to download the Excel template. Please enable 'All files access' in the settings."
                    } else {
                        "This app needs access to manage files on your device to export invoice data. Please enable 'All files access' in the settings."
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showManageStorageDialog = false
                        PermissionHelper.requestManageExternalStoragePermission(context)
                        isDownloadingTemplate = false
                    }
                ) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showManageStorageDialog = false
                        isDownloadingTemplate = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DataExportTab(
    invoices: List<Invoice>,
    customerViewModel: CustomerViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isExporting by remember { mutableStateOf(false) }
    var exportMessage by remember { mutableStateOf("") }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showManageStorageDialog by remember { mutableStateOf(false) }
    val customers by customerViewModel.allCustomers.collectAsState()
    
    // Permission launcher for export
     val exportPermissionLauncher = rememberLauncherForActivityResult(
         contract = ActivityResultContracts.RequestMultiplePermissions()
     ) { permissions ->
         val allGranted = permissions.values.all { it }
         if (allGranted || PermissionHelper.hasStoragePermissions(context)) {
             // Permissions granted, proceed with export
             scope.launch {
                 performExport(context, invoices, customers) { message ->
                     exportMessage = message
                     isExporting = false
                 }
             }
         } else {
             // Check if we should show manage storage dialog for Android 11+
             if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                 showManageStorageDialog = true
             } else {
                 exportMessage = "Storage permission is required to export data. Please grant the permission and try again."
             }
             isExporting = false
         }
     }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.FileDownload,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF10B981)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Export Invoice Data",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Export all invoice data to Excel format for backup or analysis",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B7280),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        
        if (exportMessage.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (exportMessage.contains("Successfully")) 
                        Color(0xFFD4EDDA) else Color(0xFFF8D7DA)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = exportMessage,
                    modifier = Modifier.padding(16.dp),
                    color = if (exportMessage.contains("Successfully")) 
                        Color(0xFF155724) else Color(0xFF721C24),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Total Invoices",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                        Text(
                            text = "${invoices.size}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Total Value",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                        Text(
                            text = "${invoices.sumOf { it.totalAmount }.toInt()} RSD",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                isExporting = true
                exportMessage = ""
                
                if (PermissionHelper.hasStoragePermissions(context)) {
                    // Permissions already granted, proceed with export
                    scope.launch {
                        performExport(context, invoices, customers) { message ->
                            exportMessage = message
                            isExporting = false
                        }
                    }
                } else {
                    // Request permissions using the launcher
                    val requiredPermissions = PermissionHelper.getRequiredStoragePermissions()
                    if (requiredPermissions.isNotEmpty()) {
                        exportPermissionLauncher.launch(requiredPermissions)
                    } else {
                        showPermissionDialog = true
                    }
                    isExporting = false
                }
            },
            enabled = !isExporting && invoices.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF10B981)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            if (isExporting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Icon(
                    Icons.Filled.FileDownload,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isExporting) "Exporting..." else "Export to Excel")
        }
        
        if (invoices.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No invoices to export",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Export includes:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• All invoice details (ID, customer, date, amounts)\n• Payment history and status\n• Customer information\n• Formatted for easy analysis",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280)
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long, format: String): String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(Date(timestamp))
}

private suspend fun performExport(
    context: Context,
    invoices: List<Invoice>,
    customers: List<com.focusenterprise.data.Customer>,
    onResult: (String) -> Unit
) {
    try {
        val fileName = "invoices_export_${System.currentTimeMillis()}.xlsx"
        val success = ExcelUtils.exportInvoicesToExcel(
            context,
            invoices,
            customers,
            fileName
        )
        
        if (success) {
            // Check where the file was actually saved
            val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
            val downloadsFile = java.io.File(downloadsDir, fileName)
            val internalFile = java.io.File(context.filesDir, fileName)
            
            val message = when {
                downloadsFile.exists() -> "Successfully exported ${invoices.size} invoices to Downloads folder: $fileName"
                internalFile.exists() -> "Successfully exported ${invoices.size} invoices to app storage: $fileName\n\nNote: File saved to internal storage due to permission restrictions. You can find it in the app's data folder."
                else -> "Successfully exported ${invoices.size} invoices: $fileName"
            }
            onResult(message)
        } else {
            onResult("Export failed. Please check permissions and try again.")
        }
    } catch (e: Exception) {
        android.util.Log.e("InvoiceScreen", "Export error: ${e.message}", e)
        onResult("Export failed: ${e.message ?: "Unknown error occurred"}")
    }
}

private suspend fun performTemplateDownload(
    context: Context,
    onResult: (String) -> Unit
) {
    try {
        val success = ExcelUtils.createInvoiceTemplate(
            context,
            "invoice_template_${System.currentTimeMillis()}.xlsx"
        )
        onResult(
            if (success) {
                "Template downloaded to Downloads folder"
            } else {
                "Failed to download template"
            }
        )
    } catch (e: Exception) {
        onResult("Template download failed: ${e.message}")
    }
}
