package com.focusenterprise.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.focusenterprise.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, @DrawableRes val icon: Int) {
    object Customers : Screen("customers", R.string.customers, R.drawable.ic_customer)
    object Invoices : Screen("invoices", R.string.invoices, R.drawable.ic_invoice)
    object Stock : Screen("stock", R.string.stock, R.drawable.ic_stock)
    object Expenses : Screen("expenses", R.string.expenses, R.drawable.ic_expenses)
    object Reports : Screen("reports", R.string.reports, R.drawable.ic_reports)
}
