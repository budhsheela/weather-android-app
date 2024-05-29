package com.misri.weather.network

import com.misri.weather.network.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>
}