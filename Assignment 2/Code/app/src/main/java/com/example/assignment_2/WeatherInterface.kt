package com.example.assignment_2

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherInterface {

    @GET("VisualCrossingWebServices/rest/services/timeline/Delhi/{date}?key=MMFYCCN9JDQ9TUVLSE39L84GZ&include=days&elements=tempmax,tempmin,temp")
    fun getWeather(@Path("date") date: String): Call<ResponseBody>
}