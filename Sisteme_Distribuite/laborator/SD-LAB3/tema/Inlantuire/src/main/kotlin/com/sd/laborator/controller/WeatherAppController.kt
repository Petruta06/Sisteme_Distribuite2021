package com.sd.laborator.controllers

import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
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
    private lateinit var locationSearchService: LocationSearchInterface

    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {
        // se incearca preluarea WOEID-ului locaţiei primite in URL
        val weatherForecastData = locationSearchService.getLocationId(location)

        // dacă locaţia nu a fost găsită, răspunsul va fi corespunzător
        if (weatherForecastData.location.equals("Nu am gasit locatia!")) {
            return "Nu s-au putut gasi date meteo pentru cuvintele cheie \"$location\"!"
        }

        // fiind obiect POJO, funcţia toString() este suprascrisă pentru o afişare mai prietenoasă
        return weatherForecastData.toString()
    }
}