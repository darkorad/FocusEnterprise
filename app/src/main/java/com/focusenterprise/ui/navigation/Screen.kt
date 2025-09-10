package com.focusenterprise.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.focusenterprise.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, @DrawableRes val icon: Int) {
    object Dashboard : Screen("dashboard", R.string.dashboard, R.drawable.ic_dashboard)
    object Customers : Screen("customers", R.string.customers, R.drawable.ic_customer)
    object Invoices : Screen("invoices", R.string.invoices, R.drawable.ic_invoice)
    object Stock : Screen("stock", R.string.stock, R.drawable.ic_stock)
    object Expenses : Screen("expenses", R.string.expenses, R.drawable.ic_expenses)
    object Reports : Screen("reports", R.string.reports, R.drawable.ic_reports)
    object DataManagement : Screen("data_management", R.string.data_management, R.drawable.ic_data_management)
}
