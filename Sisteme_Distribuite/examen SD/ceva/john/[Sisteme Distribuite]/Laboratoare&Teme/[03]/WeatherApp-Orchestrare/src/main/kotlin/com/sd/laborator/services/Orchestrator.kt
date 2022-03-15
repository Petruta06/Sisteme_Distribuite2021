package com.sd.laborator.services

import com.sd.laborator.interfaces.HtmlDesignerInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.OrchestratorInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Orchestrator : OrchestratorInterface{
    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var htmlDesignerService: HtmlDesignerInterface


    override fun getLocationID(locationName : String) : Int{
        return locationSearchService.getLocationId(locationName)
    }

    override fun getWeatherForecastData(locationId : Int) : WeatherForecastData{
        return weatherForecastService.getForecastData(locationId)
    }
    
    override fun processHtmlFormat(weatherForecastData : WeatherForecastData) : String{
        htmlDesignerService.InitializeHtml(weatherForecastData.location,weatherForecastData.weatherStateIconURL)
        htmlDesignerService.addHtmlElement("h1","Vremea in ",weatherForecastData.location)
        htmlDesignerService.addHtmlElement("<hr width=\"40%\" align = \"left\"/>\n")
        htmlDesignerService.addHtmlElement("<h3>")
        htmlDesignerService.addHtmlElement("<ul type=\"circle\">\n")
        htmlDesignerService.addHtmlElement("li","Data si ora",weatherForecastData.date)
        htmlDesignerService.addHtmlElement("li","Starea vremii",weatherForecastData.weatherState)
        htmlDesignerService.addHtmlElement("li","Directia vantului",weatherForecastData.windDirection)
        htmlDesignerService.addHtmlElement("li","Viteza vantului",weatherForecastData.windSpeed)
        htmlDesignerService.addHtmlElement("li","Temperatura minima",weatherForecastData.minTemp)
        htmlDesignerService.addHtmlElement("li","Temperatura maxima",weatherForecastData.maxTemp)
        htmlDesignerService.addHtmlElement("li","Temperatura curenta",weatherForecastData.currentTemp)
        htmlDesignerService.addHtmlElement("li","Umiditatea",weatherForecastData.humidity)
        htmlDesignerService.addHtmlElement("</ul>")
        htmlDesignerService.addHtmlElement("</h3")

        return htmlDesignerService.ProcessHtml()
    }
}