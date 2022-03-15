package com.sd.laborator.services

import com.sd.laborator.interfaces.DirijorOrchestra
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Dirijor:DirijorOrchestra {
    /*atrubutele clasei*/
    @Autowired
    private lateinit var  location :LocationSearchInterface

    @Autowired
    private lateinit var  weatherForecastService: WeatherForecastInterface


    override fun getLocationID(locationName: String): Int {
        return location.getLocationId(locationName)
    }

    override fun getWeatherForecastData(locationId: Int): WeatherForecastData {
        return weatherForecastService.getForecastData(locationId)
    }
}