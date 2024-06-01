package com.misri.weather

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.misri.weather.databinding.ActivityMainBinding
import com.misri.weather.network.WeatherApiClient
import com.misri.weather.network.data.WeatherResponse
import com.misri.weather.util.kelvinToCelsius
import com.misri.weather.util.meterToKmPerHour
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rdSelectValue.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbcity -> {
                    binding.searchBar.apply {
                        hint = "Enter city e.g. Pune"
                        inputType = InputType.TYPE_CLASS_TEXT
                    }

                    Toast.makeText(this@MainActivity, "City is selected", Toast.LENGTH_LONG).show()
                }

                R.id.rbPincode -> {
                    binding.searchBar.apply {
                        hint = "Enter Pincode e.g. 411057"
                        inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    Toast.makeText(this@MainActivity, "Pincode is selected", Toast.LENGTH_LONG).show()
                }


            }
        }

        binding.btnSearch.setOnClickListener {

            val searchData = binding.searchBar.text
            if(searchData.isEmpty())
            Toast.makeText(this@MainActivity, "Searching weather city data..", Toast.LENGTH_SHORT).show()
            else {
               if(binding.rbcity.isChecked)
                   callCityRetrofitApi(searchData.toString())
                else
                   callZipRetrofitApi(searchData.toString())
                Toast.makeText(this@MainActivity, "Searching weather city data..", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun callCityRetrofitApi(city: String) {
        val weatherService = WeatherApiClient.client

        val call = weatherService.getWeatherByCityName(city, WeatherApiClient.API_KEY)
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

    fun callZipRetrofitApi(zipCode: String) {
        val weatherService = WeatherApiClient.client

        val call = weatherService.getWeatherByPincode("$zipCode,in", WeatherApiClient.API_KEY)
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
        with(weatherResponse){
            binding.txtCity.text = "City name : ${name}"
            binding.txtlat.text = "Latitude : ${coord.lat}"
            binding.txtlon.text = "Longitude : ${coord.lon}"
            binding.txtTemp.text = "Temparature : ${main.temp.kelvinToCelsius()}°C"
            binding.txtWind.text = "Wind : ${wind.speed.meterToKmPerHour()}Km/h"
            binding.txtHumidity.text = "Humidity : ${main.humidity}%"
        }

    }
}
