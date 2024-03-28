package com.example.assignment_2

class WeatherRepository(private val weatherDAO: WeatherDAO) {

    suspend fun insert(weather: WeatherData) {
        weatherDAO.insertWeatherData(weather)
    }

    suspend fun getWeatherByDate(date: String): WeatherData? {
        return weatherDAO.getWeatherDataByDate(date)
    }
}