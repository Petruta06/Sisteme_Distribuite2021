package org.examen.controller

import org.examen.interfaces.LocationSearchInterface
import org.examen.interfaces.WeatherForecastInterface
import org.examen.pojo.WeatherForecastData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WeatherAppController{
    @Autowired
    private lateinit var locationSearchService : LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService : WeatherForecastInterface

    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location : String) : String{
        // se incearca preluarea WOEID-ului locatiei primite in URL
        var locationId = locationSearchService.getLocationId(location)

        // daca locatia nu a fost gasita, raspunsul va fi corespunzator
        if(locationId == -1){
            return "Nu s-au putut gasi date meteo pentru cuvintele cheie \"$location\"!"
        }

        // pe baza ID-ului de locatie, se interogheaza al doilea serviciu care returneaza datele meteo
        // incapsulare intr-un obiect POJO
        val rawForecastData : WeatherForecastData = weatherForecastService.getForecastData(locationId)

        // fiind obiect POJO, functia toString este suprascrisa pentru o afisare mai prietenoasa
        return rawForecastData.toString()
    }
}