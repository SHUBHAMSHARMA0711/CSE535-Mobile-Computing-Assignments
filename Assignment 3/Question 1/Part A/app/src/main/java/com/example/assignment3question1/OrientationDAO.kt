package com.example.assignment3question1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrientationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrientationData(orientationData: OrientationData)

    @Query("SELECT * FROM orientation_data WHERE time = :time")
    suspend fun getOrientationDataByTime(time: String): OrientationData?

    @Query("SELECT * FROM orientation_data")
    suspend fun getAllOrientationData(): List<OrientationData>

    @Query("DELETE FROM orientation_data")
    suspend fun deleteAllOrientationData()
}