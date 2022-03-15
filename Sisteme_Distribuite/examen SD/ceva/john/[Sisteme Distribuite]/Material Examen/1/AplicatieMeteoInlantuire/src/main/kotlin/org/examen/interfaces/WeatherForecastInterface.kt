package org.examen.interfaces

import org.examen.pojo.WeatherForecastData

interface WeatherForecastInterface{
    fun getForecastData(location : String) : WeatherForecastData?
}