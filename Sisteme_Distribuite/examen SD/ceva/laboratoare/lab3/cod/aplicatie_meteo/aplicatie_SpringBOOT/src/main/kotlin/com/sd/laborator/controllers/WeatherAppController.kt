package com.sd.laborator.controllers

import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import com.sd.laborator.services.LocationSearchService
import com.sd.laborator.services.WeatherForecastService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller

class WeatherAppController {
    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @RequestMapping("/getforecast/{location}", method=[RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location:String): String {
        val locationId = locationSearchService.getLocationId(location)

        if(locationId == -1)
        {
            return "nu am"
        }
        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(locationId)
        return rawForecastData.toString()
    }
}