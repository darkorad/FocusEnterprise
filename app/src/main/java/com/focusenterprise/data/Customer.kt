package com.focusenterprise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val customerId: Int = 0,
    val name: String,
    val phone: String?,
    val email: String?,
    val address: String?,
    val totalDebt: Double = 0.0
)
