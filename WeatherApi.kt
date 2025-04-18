package com.example.realtimeweatherapp

import com.example.realtimeweatherapp.Model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey: String,
        @Query("q") q: String,
    ):Response<WeatherModel>
}


