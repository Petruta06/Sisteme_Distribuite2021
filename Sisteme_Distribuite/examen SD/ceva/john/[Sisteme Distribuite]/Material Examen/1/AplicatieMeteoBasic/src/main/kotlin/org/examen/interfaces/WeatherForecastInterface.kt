package org.examen.interfaces

import org.examen.pojo.WeatherForecastData

interface WeatherForecastInterface{
    fun getForecastData(locationId : Int) : WeatherForecastData
}