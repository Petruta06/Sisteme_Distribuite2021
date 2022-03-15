package com.sd.laborator.interfaces

import com.sd.laborator.pojo.WeatherForecastData

interface OrchestratorInterface {
    fun getLocationID(locationName : String) : Int
    fun getWeatherForecastData(locationId : Int) : WeatherForecastData
    fun processHtmlFormat(weatherForecastData : WeatherForecastData) : String
}