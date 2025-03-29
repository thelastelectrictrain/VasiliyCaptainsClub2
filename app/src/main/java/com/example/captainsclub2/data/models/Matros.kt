package com.example.captainsclub2.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Matros(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isPermanent: Boolean
)