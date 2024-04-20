package com.example.assignment3question1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrientationData::class], version = 1, exportSchema = false)
abstract class OrientationDatabase : RoomDatabase() {

    abstract fun orientationDAO(): OrientationDAO

    companion object {
        @Volatile
        private var INSTANCE: OrientationDatabase? = null

        fun getDatabase(context: Context): OrientationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrientationDatabase::class.java,
                    "Orientation_DB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}