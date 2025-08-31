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
import com.focusenterprise.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(viewModel: CustomerViewModel, navController: NavController) {
    val customers by viewModel.allCustomers.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }

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
        LazyColumn(modifier = Modifier.padding(padding)) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDialog(
    customer: Customer?,
    onDismiss: () -> Unit,
    onSave: (Customer) -> Unit,
    onDelete: (Customer) -> Unit
) {
    var name by remember { mutableStateOf(customer?.name ?: "") }
    var phone by remember { mutableStateOf(customer?.phone ?: "") }
    var email by remember { mutableStateOf(customer?.email ?: "") }
    var address by remember { mutableStateOf(customer?.address ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (customer == null) "Add Customer" else "Edit Customer") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") })
                TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedCustomer = (customer ?: Customer(name = "")).copy(
                    name = name,
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
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
                if (customer != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onDelete(customer) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    )
}
