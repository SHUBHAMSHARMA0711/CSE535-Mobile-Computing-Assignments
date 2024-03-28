package com.example.assignment_2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey val date: String,
    val curTemp: String,
    val minTemp: String,
    val maxTemp: String
)