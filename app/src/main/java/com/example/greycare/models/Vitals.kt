package com.example.greycare.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "vitals_table")
data class Vitals(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val heartRate: String,
    val bloodPressure: String,
    val glucoseLevel: String,
    val timestamp: Long = System.currentTimeMillis()
)
