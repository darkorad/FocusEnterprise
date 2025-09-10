package com.focusenterprise.ui.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.focusenterprise.utils.PdfReportGenerator
import com.focusenterprise.utils.PrintReportManager
import com.focusenterprise.viewmodel.ReportViewModel
import com.focusenterprise.viewmodel.SoldArticleSummary
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(viewModel: ReportViewModel) {
    var currentMonth by remember { mutableStateOf(Calendar.getInstance()) }
    val (startDate, endDate) = getMonthDateRange(currentMonth)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val earnings by viewModel.getMonthlySalesSum(startDate, endDate).collectAsState(initial = 0.0)
    val expenses by viewModel.getMonthlyExpensesSum(startDate, endDate).collectAsState(initial = 0.0)
    val soldArticles by viewModel.getSoldArticlesSummary(startDate, endDate).collectAsState(initial = emptyList())
    val netEarnings = earnings - expenses
    
    // Permission launcher for storage access
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scope.launch {
                val file = PdfReportGenerator.generateMonthlyReport(
                    currentMonth,
                    earnings,
                    expenses,
                    soldArticles
                )
                if (file != null) {
                    Toast.makeText(context, "Report saved to Downloads: ${file.name}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to generate PDF report", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Storage permission required to save PDF", Toast.LENGTH_SHORT).show()
        }
    }

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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ReportSummary(
                    earnings = earnings,
                    expenses = expenses,
                    net = netEarnings
                )
            }
            item {
                ReportActions(
                    onExportPdf = {
                        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    },
                    onPrint = {
                        PrintReportManager.printMonthlyReport(
                            context,
                            currentMonth,
                            earnings,
                            expenses,
                            soldArticles
                        )
                    }
                )
            }
            item {
                EarningsExpensesChart(earnings = earnings, expenses = expenses)
            }
            item {
                Text("Sold Articles", style = MaterialTheme.typography.headlineSmall)
            }
            items(soldArticles) { article ->
                SoldArticleItem(article)
            }
        }
    }
}

@Composable
fun ReportSummary(earnings: Double, expenses: Double, net: Double) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Earnings: RSD ${"%.2f".format(earnings)}", style = MaterialTheme.typography.titleLarge)
            Text("Total Expenses: RSD ${"%.2f".format(expenses)}", style = MaterialTheme.typography.titleLarge)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text("Net Earnings: RSD ${"%.2f".format(net)}", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
fun EarningsExpensesChart(earnings: Double, expenses: Double) {
    val maxVal = maxOf(earnings, expenses, 1.0) // Avoid division by zero

    Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val earningsHeight = (earnings / maxVal * size.height).toFloat()
            val expensesHeight = (expenses / maxVal * size.height).toFloat()

            drawRect(
                color = Color.Green,
                topLeft = Offset(size.width / 4, size.height - earningsHeight),
                size = Size(size.width / 4, earningsHeight)
            )
            drawRect(
                color = Color.Red,
                topLeft = Offset(size.width * 2 / 4, size.height - expensesHeight),
                size = Size(size.width / 4, expensesHeight)
            )
        }
    }
}

@Composable
fun ReportActions(
    onExportPdf: () -> Unit,
    onPrint: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onExportPdf,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.PictureAsPdf,
                    contentDescription = "Export PDF",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export PDF")
            }
            
            Button(
                onClick = onPrint,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Print,
                    contentDescription = "Print",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Print")
            }
        }
    }
}

@Composable
fun SoldArticleItem(article: SoldArticleSummary) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = article.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text("Qty: ${article.totalQuantity}")
                Text("Value: RSD ${"%.2f".format(article.totalValue)}")
            }
        }
    }
}
