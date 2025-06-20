package com.example.greycare.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Vitals::class, EmergencyContact::class],
    version = 2,
    exportSchema = false
)
abstract class VitalsDatabase : RoomDatabase() {
    abstract fun vitalsDao(): VitalsDao
    abstract fun emergencyContactDao(): EmergencyContactDao

    companion object {
        @Volatile
        private var INSTANCE: VitalsDatabase? = null

        fun getDatabase(context: Context): VitalsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VitalsDatabase::class.java,
                    "vitals_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
