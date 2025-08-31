package com.focusenterprise.ui.screens

import androidx.compose.foundation.clickable
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
import com.focusenterprise.data.StockItem
import com.focusenterprise.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(viewModel: StockViewModel) {
    val stockItems by viewModel.allStockItems.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedStockItem by remember { mutableStateOf<StockItem?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedStockItem = null
                showDialog = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Stock Item")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(stockItems) { item ->
                StockListItem(item = item, onClick = {
                    selectedStockItem = item
                    showDialog = true
                })
            }
        }

        if (showDialog) {
            StockItemDialog(
                item = selectedStockItem,
                onDismiss = { showDialog = false },
                onSave = {
                    if (selectedStockItem == null) {
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
fun StockListItem(item: StockItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text(text = "Qty: ${item.quantity}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 8.dp))
            Text(text = "RSD ${"%.2f".format(item.sellingPrice)}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockItemDialog(
    item: StockItem?,
    onDismiss: () -> Unit,
    onSave: (StockItem) -> Unit,
    onDelete: (StockItem) -> Unit
) {
    var name by remember { mutableStateOf(item?.name ?: "") }
    var quantity by remember { mutableStateOf(item?.quantity?.toString() ?: "") }
    var purchasePrice by remember { mutableStateOf(item?.purchasePrice?.toString() ?: "") }
    var sellingPrice by remember { mutableStateOf(item?.sellingPrice?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (item == null) "Add Stock Item" else "Edit Stock Item") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity") })
                TextField(value = purchasePrice, onValueChange = { purchasePrice = it }, label = { Text("Purchase Price (optional)") })
                TextField(value = sellingPrice, onValueChange = { sellingPrice = it }, label = { Text("Selling Price") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedItem = (item ?: StockItem(sellingPrice = 0.0, quantity = 0, name = "")).copy(
                    name = name,
                    quantity = quantity.toIntOrNull() ?: 0,
                    purchasePrice = purchasePrice.toDoubleOrNull() ?: 0.0,
                    sellingPrice = sellingPrice.toDoubleOrNull() ?: 0.0
                )
                onSave(updatedItem)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
                if (item != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onDelete(item) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    )
}
