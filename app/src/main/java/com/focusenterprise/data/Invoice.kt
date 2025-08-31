package com.focusenterprise.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "invoices",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["customerId"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true)
    val invoiceId: Int = 0,
    val customerId: Int,
    val date: Long,
    val totalAmount: Double,
    val paidAmount: Double = 0.0,
    val status: String // "paid", "partial", "unpaid"
)
