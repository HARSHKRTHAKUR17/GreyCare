package com.example.greycare.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergency_contact_table")
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phoneNumber: String
)
