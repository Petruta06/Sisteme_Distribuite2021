package com.sd.laborator.controllers

import com.sd.laborator.interfaces.HtmlDesignerInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import com.sd.laborator.services.HtmlDesigner
import com.sd.laborator.services.Orchestrator
import com.sd.laborator.services.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WeatherAppController {
    @Autowired
    private lateinit var orchestrator: Orchestrator

    private var locationId : Int? = 0
    private var weatherForecastData : WeatherForecastData? = null

    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {

        locationId = orchestrator.getLocationID(location)
        weatherForecastData = orchestrator.getWeatherForecastData(locationId!!)
        return orchestrator.processHtmlFormat(weatherForecastData!!)
    }
}