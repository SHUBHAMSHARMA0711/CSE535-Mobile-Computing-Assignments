package com.example.assignment3question1

class OrientationRepository(private val orientationDAO: OrientationDAO) {

    suspend fun insert(orientation: OrientationData) {
        orientationDAO.insertOrientationData(orientation)
    }

    suspend fun getOrientationByTime(time: String): OrientationData? {
        return orientationDAO.getOrientationDataByTime(time)
    }

    suspend fun getAllOrientationData(): List<OrientationData> {
        return orientationDAO.getAllOrientationData()
    }

    suspend fun deleteAllOrientationData() {
        orientationDAO.deleteAllOrientationData()
    }
}