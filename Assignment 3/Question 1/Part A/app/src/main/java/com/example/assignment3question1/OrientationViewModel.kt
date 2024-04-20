package com.example.assignment3question1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrientationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrientationRepository

    init {
        val orientationDAO = OrientationDatabase.getDatabase(application).orientationDAO()
        repository = OrientationRepository(orientationDAO)
    }

    fun insert(orientation: OrientationData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(orientation)
        }
    }

    suspend fun getOrientationByTime(time: String): OrientationData? {
        return repository.getOrientationByTime(time)
    }

    suspend fun getAllOrientationData(): List<OrientationData> {
        return repository.getAllOrientationData()
    }

    suspend fun deleteAllOrientationData() {
        repository.deleteAllOrientationData()
    }
}