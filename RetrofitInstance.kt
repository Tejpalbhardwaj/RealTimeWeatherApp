package com.example.realtimeweatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ed9b4e59381048e1afa114614251301   apikey
//http://api.weatherapi.com/v1/current.json ResponseBody Url

object RetrofitInstance {

    private val baseUrl = "https://api.weatherapi.com"

    private fun RetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherapi: WeatherApi = RetrofitInstance().create(WeatherApi::class.java)

}