package com.misri.weather

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.misri.weather.databinding.ActivityMainBinding
import com.misri.weather.network.WeatherApiClient
import com.misri.weather.network.WeatherData
import com.misri.weather.network.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

//        // Replace "CityName" with the desired city
//        GlobalScope.launch(Dispatchers.IO) {
//            val weatherData = weatherService.getWeather("London", WeatherApiClient.API_KEY)
//            withContext(Dispatchers.Main) {
//                updateUI(weatherData)
//            }
//        }

        val call = weatherService.getWeather("Azamgarh", WeatherApiClient.API_KEY)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.main?.temperature
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

    private fun updateUI(weatherData: WeatherData) {

        val sb = StringBuilder()
        sb.append(weatherData.name).append("${weatherData.main.temp.toInt()}°C")
            .append("icon path ${weatherData.weather[0].icon}")
       // val iconUrl = "https://openweathermap.org/img/w/${weatherData.weather[0].icon}.png"
//        Glide.with(this)
//            .load(iconUrl)
//            .into(findViewById(R.id.imageViewWeatherIcon))
    }
}
