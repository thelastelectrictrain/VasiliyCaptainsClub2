package com.example.captainsclub2.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Operation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "RENTAL", "SALE", "FINE"
    val itemType: String, // "DUCK", "YACHT", "SOUVENIR", etc.
    val itemId: Int, // Item ID (e.g., duck number)
    val startTime: String, // Format: HH:mm:ss
    val endTime: String, // Format: HH:mm:ss
    val status: String, // "COMPLETED", "CANCELED"
    val isCash: Boolean,
    val hasDiscount: Boolean
)