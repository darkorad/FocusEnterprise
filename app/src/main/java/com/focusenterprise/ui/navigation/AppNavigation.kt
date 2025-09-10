package com.focusenterprise.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Dashboard,
        Screen.Customers,
        Screen.Invoices,
        Screen.Stock,
        Screen.Expenses,
        Screen.Reports,
        Screen.DataManagement,
    )

    val application = LocalContext.current.applicationContext as FocusEnterpriseApplication
    val viewModelFactory = ViewModelFactory(application)
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("Focus Enterprise") },
                    selected = false,
                    onClick = { },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                Divider()
                items.forEach { screen ->
                    NavigationDrawerItem(
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
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            text = items.find { currentDestination?.hierarchy?.any { dest -> dest.route == it.route } == true }
                                ?.let { stringResource(it.resourceId) } ?: "Focus Enterprise"
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Dashboard.route, Modifier.padding(innerPadding)) {
            composable(Screen.Dashboard.route) {
                val dashboardViewModel: DashboardViewModel = viewModel(factory = viewModelFactory)
                DashboardScreen(dashboardViewModel)
            }
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
            composable(Screen.DataManagement.route) {
                val dataManagementViewModel: DataManagementViewModel = viewModel(factory = viewModelFactory)
                DataManagementScreen(dataManagementViewModel)
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
}
