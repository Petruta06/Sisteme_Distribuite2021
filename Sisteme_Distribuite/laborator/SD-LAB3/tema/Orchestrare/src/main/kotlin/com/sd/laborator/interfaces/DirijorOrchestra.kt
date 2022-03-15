package com.sd.laborator.interfaces

import com.sd.laborator.pojo.WeatherForecastData



interface DirijorOrchestra {
     fun getLocationID(locationName : String) : Int
     fun getWeatherForecastData(locationId : Int) : WeatherForecastData

}