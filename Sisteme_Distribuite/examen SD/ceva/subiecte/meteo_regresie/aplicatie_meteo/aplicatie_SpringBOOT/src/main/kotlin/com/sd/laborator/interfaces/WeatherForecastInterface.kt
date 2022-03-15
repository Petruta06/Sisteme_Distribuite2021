package com.sd.laborator.interfaces

import com.sd.laborator.pojo.WeatherForecastData

interface WeatherForecastInterface  {
    public fun getForecastData(locationId:Int): WeatherForecastData
}