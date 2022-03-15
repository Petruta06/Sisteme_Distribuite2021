package com.sd.laborator.interfaces

import com.sd.laborator.pojo.WeatherForecastData


interface LocationSearchInterface {
    fun getLocationId(locationName:String): WeatherForecastData

}