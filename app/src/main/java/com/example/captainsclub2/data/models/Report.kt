package com.example.captainsclub2.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Report(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val shiftId: Int,
    val data: String // JSON or plain text
)