package com.focusenterprise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    val invoices by invoiceViewModel.allInvoices.collectAsState()
    val customers by customerViewModel.allCustomers.collectAsState()
    var showPaymentDialog by remember { mutableStateOf<Invoice?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("create_invoice") }) {
                Icon(Icons.Filled.Add, contentDescription = "Create Invoice")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(invoices) { invoice ->
                val customerName = customers.find { it.customerId == invoice.customerId }?.name ?: "N/A"
                InvoiceListItem(
                    invoice = invoice,
                    customerName = customerName,
                    onAddPaymentClick = { showPaymentDialog = invoice }
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
    }
}

@Composable
fun InvoiceListItem(invoice: Invoice, customerName: String, onAddPaymentClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Invoice #${invoice.invoiceId}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "To: $customerName", style = MaterialTheme.typography.bodyMedium)
                    Text(text = formatDate(invoice.date, "dd MMM yyyy"), style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "RSD ${"%.2f".format(invoice.totalAmount)}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = invoice.status.uppercase(), style = MaterialTheme.typography.labelMedium)
                }
            }
            if (invoice.status != "paid") {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onAddPaymentClick) {
                    Text("Add Payment")
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
        title = { Text("Add Payment for Invoice #${invoice.invoiceId}") },
        text = {
            Column {
                Text("Remaining amount: RSD ${"%.2f".format(remaining)}")
                TextField(value = amount, onValueChange = { amount = it }, label = { Text("Payment Amount") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val paymentAmount = amount.toDoubleOrNull()
                if (paymentAmount != null && paymentAmount > 0) {
                    onSave(paymentAmount)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun formatDate(timestamp: Long, format: String): String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(Date(timestamp))
}
