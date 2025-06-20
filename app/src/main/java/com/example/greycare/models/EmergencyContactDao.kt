package com.example.greycare.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: EmergencyContact)

    @Query("SELECT * FROM emergency_contact_table LIMIT 1")
    fun getEmergencyContact(): Flow<EmergencyContact?>

    @Delete
    suspend fun delete(contact: EmergencyContact)
}
