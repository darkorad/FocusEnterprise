package com.focusenterprise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int = 0,
    val category: String,
    val amount: Double,
    val date: Long,
    val notes: String?
)
