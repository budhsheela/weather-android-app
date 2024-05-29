package com.misri.weather

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.misri.weather.databinding.ActivityMainBinding
import com.misri.weather.network.WeatherApiClient
import com.misri.weather.network.data.WeatherResponse
import com.misri.weather.util.kelvinToCelsius
import com.misri.weather.util.meterToKmPerHour
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {

            val city = binding.searchBar.text
            if(city.isEmpty())
            Toast.makeText(this@MainActivity, "Searching weather city data..", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(this@MainActivity, "Searching weather city data..", Toast.LENGTH_SHORT).show()
                callRetrofitApi(city.toString())
            }
        }
    }

    fun callRetrofitApi(city: String) {
        val weatherService = WeatherApiClient.client

        val call = weatherService.getWeather(city, WeatherApiClient.API_KEY)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse: WeatherResponse? =response.body()
                    if(weatherResponse!=null) parseResponse(weatherResponse)
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch weather data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to fetch weather data", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun parseResponse(weatherResponse: WeatherResponse){
        // TODO: fill the response into UI
        binding.txtlat.text = "Latitude : ${weatherResponse.coord.lat}"
        binding.txtlon.text = "Longitude : ${weatherResponse.coord.lon}"
        binding.txtTemp.text = "Temparature : ${weatherResponse.main.temp.kelvinToCelsius()}°C"
        binding.txtWind.text = "Wind : ${weatherResponse.wind.speed.meterToKmPerHour()}Km/h"
        binding.txtHumidity.text = "Humidity : ${weatherResponse.main.humidity}%"
    }
}
