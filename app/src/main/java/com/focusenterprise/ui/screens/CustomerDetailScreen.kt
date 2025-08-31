package com.focusenterprise.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.focusenterprise.data.Invoice
import com.focusenterprise.data.Payment
import com.focusenterprise.viewmodel.CustomerDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CustomerDetailScreen(viewModel: CustomerDetailViewModel) {
    val customer by viewModel.customer.collectAsState(initial = null)
    val invoices by viewModel.invoices.collectAsState(initial = emptyList())
    val payments by viewModel.payments.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            customer?.let {
                Text(it.name, style = MaterialTheme.typography.headlineMedium)
                Text(it.phone ?: "", style = MaterialTheme.typography.bodyLarge)
                Text(it.email ?: "", style = MaterialTheme.typography.bodyLarge)
                Text(it.address ?: "", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "Total Debt: RSD ${"%.2f".format(it.totalDebt)}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        item {
            Text("Invoices", style = MaterialTheme.typography.headlineSmall)
        }
        items(invoices) { invoice ->
            val context = LocalContext.current
            InvoiceHistoryItem(invoice, onExport = {
                viewModel.exportInvoiceToPdf(context, invoice)
                Toast.makeText(context, "Saving PDF...", Toast.LENGTH_SHORT).show()
            })
        }

        item {
            Text("Payments", style = MaterialTheme.typography.headlineSmall)
        }
        items(payments) { payment ->
            PaymentHistoryItem(payment)
        }
    }
}

@Composable
fun InvoiceHistoryItem(invoice: Invoice, onExport: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Invoice #${invoice.invoiceId}")
            Text("Date: ${formatDate(invoice.date)}")
            Text("Total: RSD ${"%.2f".format(invoice.totalAmount)}")
            Text("Status: ${invoice.status}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onExport) {
                Text("Export as PDF")
            }
        }
    }
}

@Composable
fun PaymentHistoryItem(payment: Payment) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Payment #${payment.paymentId} for Invoice #${payment.invoiceId}")
            Text("Date: ${formatDate(payment.date)}")
            Text("Amount: RSD ${"%.2f".format(payment.amount)}")
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
