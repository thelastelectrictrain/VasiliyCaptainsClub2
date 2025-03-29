package com.example.captainsclub2.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shift(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    var status: Boolean, // true = open, false = closed
    val matrosOnShift: List<Int> // IDs of matros on shift
)