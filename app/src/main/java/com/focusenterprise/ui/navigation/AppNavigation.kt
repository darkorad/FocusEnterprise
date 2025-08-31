package com.focusenterprise.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.focusenterprise.FocusEnterpriseApplication
import com.focusenterprise.ui.screens.*
import com.focusenterprise.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Customers,
        Screen.Invoices,
        Screen.Stock,
        Screen.Expenses,
        Screen.Reports,
    )

    val application = LocalContext.current.applicationContext as FocusEnterpriseApplication
    val viewModelFactory = ViewModelFactory(application)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(painterResource(id = screen.icon), contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Invoices.route, Modifier.padding(innerPadding)) {
            composable(Screen.Stock.route) {
                val stockViewModel: StockViewModel = viewModel(factory = viewModelFactory)
                StockScreen(stockViewModel)
            }
            composable(Screen.Customers.route) {
                val customerViewModel: CustomerViewModel = viewModel(factory = viewModelFactory)
                CustomerScreen(customerViewModel, navController)
            }
            composable(Screen.Invoices.route) {
                val invoiceViewModel: InvoiceViewModel = viewModel(factory = viewModelFactory)
                val customerViewModel: CustomerViewModel = viewModel(factory = viewModelFactory)
                val paymentViewModel: PaymentViewModel = viewModel(factory = viewModelFactory)
                InvoiceScreen(invoiceViewModel, customerViewModel, paymentViewModel, navController)
            }
            composable(Screen.Expenses.route) {
                val expenseViewModel: ExpenseViewModel = viewModel(factory = viewModelFactory)
                ExpenseScreen(expenseViewModel)
            }
            composable(Screen.Reports.route) {
                val reportViewModel: ReportViewModel = viewModel(factory = viewModelFactory)
                ReportScreen(reportViewModel)
            }
            composable("create_invoice") {
                CreateInvoiceScreen(navController)
            }
            composable("customer_detail/{customerId}") { backStackEntry ->
                val customerId = backStackEntry.arguments?.getString("customerId")?.toIntOrNull()
                if (customerId != null) {
                    val detailViewModel: CustomerDetailViewModel = viewModel(
                        factory = CustomerDetailViewModelFactory(customerId, application)
                    )
                    CustomerDetailScreen(detailViewModel)
                }
            }
        }
    }
}
