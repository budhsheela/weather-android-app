package com.misri.weather.network

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val name: String,
    @SerializedName("main") val main: Main,
    val weather: List<Weather>
) {
    data class Main(
        @SerializedName("temp") val temperature: Float
    )

    data class Weather(
        @SerializedName("icon") val icon: String
    )
}