package com.focusenterprise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_items")
data class StockItem(
    @PrimaryKey(autoGenerate = true)
    val stockId: Int = 0,
    val name: String,
    val purchasePrice: Double = 0.0,
    val sellingPrice: Double,
    val quantity: Int
)
