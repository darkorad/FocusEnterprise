package com.focusenterprise.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "invoice_items",
    foreignKeys = [
        ForeignKey(
            entity = Invoice::class,
            parentColumns = ["invoiceId"],
            childColumns = ["invoiceId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StockItem::class,
            parentColumns = ["stockId"],
            childColumns = ["stockId"],
            onDelete = ForeignKey.RESTRICT // Don't delete stock item if it's in an invoice
        )
    ],
    indices = [Index(value = ["invoiceId"]), Index(value = ["stockId"])]
)
data class InvoiceItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val invoiceId: Int,
    val stockId: Int,
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double
)
