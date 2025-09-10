package com.focusenterprise.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focusenterprise.viewmodel.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val todayRevenue by viewModel.getTodayRevenue().collectAsState(initial = 0.0)
    val monthlyRevenue by viewModel.getMonthlyRevenue().collectAsState(initial = 0.0)
    val totalCustomers by viewModel.getTotalCustomers().collectAsState(initial = 0)
    val pendingInvoices by viewModel.getPendingInvoices().collectAsState(initial = 0)
    val lowStockItems by viewModel.getLowStockItems().collectAsState(initial = 0)
    val recentActivities by viewModel.getRecentActivities().collectAsState(initial = emptyList())
    val salesData by viewModel.getWeeklySalesData().collectAsState(initial = emptyList())
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Focus Enterprise",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault()).format(Date()),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Key Metrics Cards
            item {
                Text(
                    "Business Overview",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listOf(
                        MetricCard("Today's Revenue", "RSD ${"%.2f".format(todayRevenue)}", Icons.Filled.TrendingUp, Color(0xFF4CAF50)),
                        MetricCard("Monthly Revenue", "RSD ${"%.2f".format(monthlyRevenue)}", Icons.Filled.AccountBalance, Color(0xFF2196F3)),
                        MetricCard("Total Customers", totalCustomers.toString(), Icons.Filled.People, Color(0xFF9C27B0)),
                        MetricCard("Pending Invoices", pendingInvoices.toString(), Icons.Filled.Receipt, Color(0xFFFF9800))
                    )) { metric ->
                        MetricCardItem(metric)
                    }
                }
            }
            
            // Quick Actions
            item {
                Text(
                    "Quick Actions",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listOf(
                        QuickAction("New Invoice", Icons.Filled.Add, Color(0xFF4CAF50)),
                        QuickAction("Add Customer", Icons.Filled.PersonAdd, Color(0xFF2196F3)),
                        QuickAction("Stock Check", Icons.Filled.Inventory, Color(0xFFFF9800)),
                        QuickAction("Reports", Icons.Filled.Assessment, Color(0xFF9C27B0))
                    )) { action ->
                        QuickActionItem(action)
                    }
                }
            }
            
            // Sales Chart
            item {
                Text(
                    "Weekly Sales Trend",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            item {
                SalesChart(salesData)
            }
            
            // Alerts Section
            item {
                AlertsSection(lowStockItems)
            }
            
            // Recent Activities
            item {
                Text(
                    "Recent Activities",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            items(recentActivities) { activity ->
                ActivityItem(activity)
            }
        }
    }
}

data class MetricCard(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color
)

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val color: Color
)

data class Activity(
    val title: String,
    val description: String,
    val timestamp: String,
    val icon: ImageVector
)

@Composable
fun MetricCardItem(metric: MetricCard) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = metric.color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = metric.icon,
                    contentDescription = null,
                    tint = metric.color,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = metric.value,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = metric.color
                )
                Text(
                    text = metric.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun QuickActionItem(action: QuickAction) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = action.color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                tint = action.color,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = action.title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SalesChart(salesData: List<Double>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (salesData.isNotEmpty()) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val maxValue = salesData.maxOrNull() ?: 1.0
                    val stepX = size.width / (salesData.size - 1).coerceAtLeast(1)
                    
                    for (i in salesData.indices) {
                        val x = i * stepX
                        val y = size.height - (salesData[i] / maxValue * size.height).toFloat()
                        
                        drawCircle(
                            color = Color(0xFF2196F3),
                            radius = 4.dp.toPx(),
                            center = Offset(x, y)
                        )
                        
                        if (i < salesData.size - 1) {
                            val nextX = (i + 1) * stepX
                            val nextY = size.height - (salesData[i + 1] / maxValue * size.height).toFloat()
                            
                            drawLine(
                                color = Color(0xFF2196F3),
                                start = Offset(x, y),
                                end = Offset(nextX, nextY),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No sales data available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun AlertsSection(lowStockItems: Int) {
    if (lowStockItems > 0) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFF5722).copy(alpha = 0.1f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = Color(0xFFFF5722),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Low Stock Alert",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFFFF5722)
                    )
                    Text(
                        "$lowStockItems items are running low on stock",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityItem(activity: Activity) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = activity.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = activity.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = activity.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}