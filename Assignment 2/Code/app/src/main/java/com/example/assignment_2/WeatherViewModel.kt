package com.example.assignment_2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository

    init {
        val weatherDAO = WeatherDatabase.getDatabase(application).weatherDAO()
        repository = WeatherRepository(weatherDAO)
    }

    fun insert(weather: WeatherData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(weather)
        }
    }

    suspend fun getWeatherByDate(date: String): WeatherData? {
        return repository.getWeatherByDate(date)
    }
}