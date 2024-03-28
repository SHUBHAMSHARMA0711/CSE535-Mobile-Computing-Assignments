package com.example.assignment_2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherData)

    @Query("SELECT * FROM weather_data WHERE date = :date")
    suspend fun getWeatherDataByDate(date: String): WeatherData?
}