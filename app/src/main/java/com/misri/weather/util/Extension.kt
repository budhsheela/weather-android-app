package com.misri.weather.util

/**
 * The temperature T in degrees Celsius (°C) is equal to the temperature T in Kelvin (K) minus 273.15:
 * T(°C) = T(K) - 273.15
 *
 * Example
 * Convert 300 Kelvin to degrees Celsius:
 * T(°C) = 300K - 273.15 = 26.85 °C
 */
fun Double.kelvinToCelsius() : Int {

    return  (this - 273.15).toInt()
}
/*
* Calculate wind speed m/s to km/h
*/
fun Double.meterToKmPerHour() : Float {

    return  ((this*18)/5).toFloat()
}