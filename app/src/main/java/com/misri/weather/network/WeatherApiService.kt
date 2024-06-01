package com.misri.weather.network

import com.misri.weather.network.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>

    @GET("weather")
    fun getWeatherByPincode(
        @Query("zip") zipcode: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>

    @GET("weather")
    fun getWeatherByLatLon(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>
}