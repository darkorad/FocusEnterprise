package com.focusenterprise.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.focusenterprise.FocusEnterpriseApplication
import com.focusenterprise.data.Customer
import com.focusenterprise.data.InvoiceItem
import com.focusenterprise.data.StockItem
import com.focusenterprise.viewmodel.CreateInvoiceViewModel
import com.focusenterprise.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateInvoiceScreen(navController: NavController) {
    val application = LocalContext.current.applicationContext as FocusEnterpriseApplication
    val viewModel: CreateInvoiceViewModel = viewModel(factory = ViewModelFactory(application))

    val customers by viewModel.allCustomers.collectAsState(initial = emptyList())
    val selectedCustomer by viewModel.selectedCustomer.collectAsState()
    val lineItems by viewModel.lineItems.collectAsState()
    val totalAmount by viewModel.totalAmount.collectAsState()
    val validationError by viewModel.validationError.collectAsState()

    var showAddItemDialog by remember { mutableStateOf(false) }

    LaunchedEffect(validationError) {
        validationError?.let {
            Toast.makeText(application, it, Toast.LENGTH_SHORT).show()
            if (it == "Invoice Saved!") {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Create Invoice") }) },
        floatingActionButton = {
            Button(onClick = { viewModel.saveInvoice() }) {
                Text("Save Invoice")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                CustomerSelector(customers, selectedCustomer, onCustomerSelected = { viewModel.setCustomer(it) })
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text("Line Items", style = MaterialTheme.typography.headlineSmall)
                Button(onClick = { showAddItemDialog = true }) {
                    Text("Add Item")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(lineItems) { item ->
                val stockItem = viewModel.allStockItems.collectAsState().value.find { it.stockId == item.stockId }
                LineItemRow(item, stockItem?.name ?: "Unknown") { viewModel.removeLineItem(item) }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Total: RSD ${"%.2f".format(totalAmount)}", style = MaterialTheme.typography.headlineMedium)
            }
        }

        if (showAddItemDialog) {
            AddItemDialog(
                stockItems = viewModel.allStockItems.collectAsState().value,
                onDismiss = { showAddItemDialog = false },
                onAddItem = { stockItem, quantity ->
                    viewModel.addLineItem(stockItem, quantity)
                    showAddItemDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerSelector(
    customers: List<Customer>,
    selectedCustomer: Customer?,
    onCustomerSelected: (Customer) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCustomer?.name ?: "Select a customer",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            customers.forEach { customer ->
                DropdownMenuItem(
                    text = { Text(customer.name) },
                    onClick = {
                        onCustomerSelected(customer)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun LineItemRow(item: InvoiceItem, itemName: String, onRemove: () -> Unit) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "$itemName (Qty: ${item.quantity})", modifier = Modifier.weight(1f))
        Text(text = "RSD ${"%.2f".format(item.totalPrice)}")
        Button(onClick = onRemove) { Text("X") }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
    stockItems: List<StockItem>,
    onDismiss: () -> Unit,
    onAddItem: (StockItem, Int) -> Unit
) {
    var selectedStockItem by remember { mutableStateOf<StockItem?>(null) }
    var quantity by remember { mutableStateOf("1") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Item to Invoice") },
        text = {
            Column {
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    TextField(
                        value = selectedStockItem?.name ?: "Select stock item",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        stockItems.forEach { item ->
                            DropdownMenuItem(
                                text = { Text("${item.name} (Price: ${item.sellingPrice})") },
                                onClick = {
                                    selectedStockItem = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                TextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val qty = quantity.toIntOrNull() ?: 0
                if (selectedStockItem != null && qty > 0) {
                    onAddItem(selectedStockItem!!, qty)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
