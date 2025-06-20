package com.example.greycare.models

import androidx.room.Update
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {

    @Insert
    suspend fun insert(vitals: Vitals)

    @Query("SELECT * FROM vitals_table ORDER BY timestamp DESC")
    fun getAllVitals(): Flow<List<Vitals>>

    @Delete
    suspend fun delete(vitals: Vitals)

    @Update
    suspend fun update(vitals: Vitals)

}
