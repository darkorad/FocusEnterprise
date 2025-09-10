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
import com.focusenterprise.data.Expense
import com.focusenterprise.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    var currentMonth by remember { mutableStateOf(Calendar.getInstance()) }
    val (startDate, endDate) = getMonthDateRange(currentMonth)
    val expenses by viewModel.getExpensesByDateRange(startDate, endDate).collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }

    Scaffold(
        topBar = {
            MonthSelector(
                calendar = currentMonth,
                onPrevious = {
                    val newCalendar = currentMonth.clone() as Calendar
                    newCalendar.add(Calendar.MONTH, -1)
                    currentMonth = newCalendar
                },
                onNext = {
                    val newCalendar = currentMonth.clone() as Calendar
                    newCalendar.add(Calendar.MONTH, 1)
                    currentMonth = newCalendar
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedExpense = null
                showDialog = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Expense")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(expenses) { expense ->
                ExpenseListItem(expense = expense, onClick = {
                    selectedExpense = expense
                    showDialog = true
                })
            }
        }

        if (showDialog) {
            ExpenseDialog(
                expense = selectedExpense,
                onDismiss = { showDialog = false },
                onSave = {
                    if (selectedExpense == null) viewModel.insert(it) else viewModel.update(it)
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
fun MonthSelector(calendar: Calendar, onPrevious: () -> Unit, onNext: () -> Unit) {
    val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onPrevious) { Text("<") }
        Text(text = monthFormat.format(calendar.time), style = MaterialTheme.typography.headlineSmall)
        Button(onClick = onNext) { Text(">") }
    }
}

@Composable
fun ExpenseListItem(expense: Expense, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = expense.category, style = MaterialTheme.typography.titleMedium)
                Text(text = expense.notes ?: "", style = MaterialTheme.typography.bodySmall)
            }
            Text(text = "RSD ${"%.2f".format(expense.amount)}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDialog(expense: Expense?, onDismiss: () -> Unit, onSave: (Expense) -> Unit, onDelete: (Expense) -> Unit) {
    val categories = listOf("Promotions", "Marketing", "Fairs", "Payroll", "Incentives", "Custom")
    var category by remember { mutableStateOf(expense?.category ?: categories.first()) }
    var amount by remember { mutableStateOf(expense?.amount?.toString() ?: "") }
    var notes by remember { mutableStateOf(expense?.notes ?: "") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (expense == null) "Add Expense" else "Edit Expense") },
        text = {
            Column {
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    TextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        categories.forEach { cat ->
                            DropdownMenuItem(text = { Text(cat) }, onClick = {
                                category = cat
                                expanded = false
                            })
                        }
                    }
                }
                TextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") })
                TextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes (optional)") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedExpense = (expense ?: Expense(category = "", amount = 0.0, date = 0L, notes = null)).copy(
                    category = category,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    notes = notes,
                    date = expense?.date ?: System.currentTimeMillis()
                )
                onSave(updatedExpense)
            }) { Text("Save") }
        },
        dismissButton = {
            Row {
                Button(onClick = onDismiss) { Text("Cancel") }
                if (expense != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onDelete(expense) }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                        Text("Delete")
                    }
                }
            }
        }
    )
}

fun getMonthDateRange(calendar: Calendar): Pair<Long, Long> {
    val start = calendar.clone() as Calendar
    start.set(Calendar.DAY_OF_MONTH, 1)
    start.set(Calendar.HOUR_OF_DAY, 0)
    start.set(Calendar.MINUTE, 0)
    start.set(Calendar.SECOND, 0)

    val end = calendar.clone() as Calendar
    end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH))
    end.set(Calendar.HOUR_OF_DAY, 23)
    end.set(Calendar.MINUTE, 59)
    end.set(Calendar.SECOND, 59)

    return Pair(start.timeInMillis, end.timeInMillis)
}
