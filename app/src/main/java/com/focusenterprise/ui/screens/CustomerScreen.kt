package com.focusenterprise.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.focusenterprise.data.Customer
import com.focusenterprise.data.CustomerMonthlySummary
import com.focusenterprise.viewmodel.CustomerViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(viewModel: CustomerViewModel, navController: NavController) {
    val customers by viewModel.allCustomers.collectAsState()
    val monthlySummaries by viewModel.customerMonthlySummaries.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var showMonthlySummaries by remember { mutableStateOf(true) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedCustomer = null
                showDialog = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Customer")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Toggle button to switch between views
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElevatedButton(
                    onClick = { showMonthlySummaries = true },
                    colors = if (showMonthlySummaries) ButtonDefaults.elevatedButtonColors() else ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("Monthly Summaries")
                }
                ElevatedButton(
                    onClick = { showMonthlySummaries = false },
                    colors = if (!showMonthlySummaries) ButtonDefaults.elevatedButtonColors() else ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("All Customers")
                }
            }
            
            LazyColumn {
                if (showMonthlySummaries) {
                    items(monthlySummaries) { summary ->
                        CustomerMonthlySummaryItem(
                            summary = summary,
                            onDetailClick = {
                                navController.navigate("customer_detail/${summary.customerId}")
                            }
                        )
                    }
                } else {
                    items(customers) { customer ->
                        CustomerListItem(
                            customer = customer,
                            onEditClick = {
                                selectedCustomer = customer
                                showDialog = true
                            },
                            onDetailClick = {
                                navController.navigate("customer_detail/${customer.customerId}")
                            }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            CustomerDialog(
                customer = selectedCustomer,
                onDismiss = { showDialog = false },
                onSave = {
                    if (selectedCustomer == null) {
                        viewModel.insert(it)
                    } else {
                        viewModel.update(it)
                    }
                    showDialog = false
                },
                onDelete = {
                    viewModel.delete(it)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun CustomerMonthlySummaryItem(summary: CustomerMonthlySummary, onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = summary.customerName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formatMonthYear(summary.monthYear),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDetailClick) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "View Details")
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Invoices: ${summary.invoiceCount}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Total: RSD ${"%.2f".format(summary.totalInvoiceAmount)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Paid: RSD ${"%.2f".format(summary.totalPaidAmount)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Debt: RSD ${"%.2f".format(summary.remainingDebt)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (summary.remainingDebt > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerListItem(customer: Customer, onEditClick: () -> Unit, onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onEditClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = customer.name, style = MaterialTheme.typography.titleMedium)
                Text(text = customer.phone ?: "", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = "RSD ${"%.2f".format(customer.totalDebt)}", style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = onDetailClick) {
                Icon(Icons.Default.ArrowForward, contentDescription = "View Details")
            }
        }
    }
}

private fun formatMonthYear(monthYear: String): String {
    return try {
        val parts = monthYear.split("-")
        val year = parts[0]
        val month = parts[1].toInt()
        val monthNames = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        "${monthNames[month - 1]} $year"
    } catch (e: Exception) {
        monthYear
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDialog(
    customer: Customer?,
    onDismiss: () -> Unit,
    onSave: (Customer) -> Unit,
    onDelete: (Customer) -> Unit
) {
    var name by remember { mutableStateOf(customer?.name ?: "") }
    var pib by remember { mutableStateOf(customer?.pib ?: "") }
    var phone by remember { mutableStateOf(customer?.phone ?: "") }
    var email by remember { mutableStateOf(customer?.email ?: "") }
    var address by remember { mutableStateOf(customer?.address ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (customer == null) "Add Customer" else "Edit Customer") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = pib, onValueChange = { pib = it }, label = { Text("PIB") })
                TextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") })
                TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
            }
        },
        confirmButton = {
            ElevatedButton(onClick = {
                val updatedCustomer = (customer ?: Customer(
                    name = "",
                    pib = null,
                    phone = null,
                    email = null,
                    address = null
                )).copy(
                    name = name,
                    pib = pib.takeIf { it.isNotBlank() },
                    phone = phone.takeIf { it.isNotBlank() },
                    email = email.takeIf { it.isNotBlank() },
                    address = address.takeIf { it.isNotBlank() }
                )
                onSave(updatedCustomer)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                if (customer != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    FilledTonalButton(
                        onClick = { onDelete(customer) },
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    )
}
