package com.focusenterprise.data

import androidx.room.Ignore

data class CustomerMonthlySummary(
    val customerId: Int,
    val customerName: String,
    val monthYear: String, // Format: "YYYY-MM"
    val totalInvoiceAmount: Double,
    val totalPaidAmount: Double,
    val invoiceCount: Int
) {
    @get:Ignore
    val remainingDebt: Double
        get() = totalInvoiceAmount - totalPaidAmount
}