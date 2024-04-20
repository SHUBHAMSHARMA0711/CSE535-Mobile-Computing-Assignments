package com.example.assignment3question1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orientation_data")
data class OrientationData(
    @PrimaryKey val time: String,
    val pitch: Float,
    val roll: Float,
    val yaw: Float
)