package com.misri.weather

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.misri.weather.databinding.ActivityMainBinding
import com.misri.weather.network.WeatherApiClient
import com.misri.weather.network.data.WeatherResponse
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
            Toast.makeText(this@MainActivity, "Searching weather city data..", Toast.LENGTH_SHORT).show()
            callRetrofitApi()
        }
    }

    fun callRetrofitApi() {
        val weatherService = WeatherApiClient.client

        val call = weatherService.getWeather("Azamgarh", WeatherApiClient.API_KEY)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.main?.temp
                    binding.textView.text = "Api data: ${response.body()?.main}, ${response.body()?.name}, ${response.body()?.weather?.get(0)}"
                } else {
                    binding.textView.text = "Failed to fetch weather data"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                binding.textView.text = "Failed to fetch weather data"
            }
        })
    }
}
