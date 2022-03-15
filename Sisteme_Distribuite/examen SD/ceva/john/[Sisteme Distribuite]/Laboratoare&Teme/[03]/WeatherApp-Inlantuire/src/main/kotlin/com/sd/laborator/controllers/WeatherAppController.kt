package com.sd.laborator.controllers

import com.sd.laborator.interfaces.HtmlDesignerInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import com.sd.laborator.services.HtmlDesigner
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
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var htmlDesigner: HtmlDesignerInterface

    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {

        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(location)

        htmlDesigner.InitializeHtml(rawForecastData.location,rawForecastData.weatherStateIconURL)
        htmlDesigner.addHtmlElement("h1","Vremea in ",rawForecastData.location)
        htmlDesigner.addHtmlElement("<hr width=\"40%\" align = \"left\"/>\n")
        htmlDesigner.addHtmlElement("<h3>")
        htmlDesigner.addHtmlElement("<ul type=\"circle\">\n")
        htmlDesigner.addHtmlElement("li","Data si ora",rawForecastData.date)
        htmlDesigner.addHtmlElement("li","Starea vremii",rawForecastData.weatherState)
        htmlDesigner.addHtmlElement("li","Directia vantului",rawForecastData.windDirection)
        htmlDesigner.addHtmlElement("li","Viteza vantului",rawForecastData.windSpeed)
        htmlDesigner.addHtmlElement("li","Temperatura minima",rawForecastData.minTemp)
        htmlDesigner.addHtmlElement("li","Temperatura maxima",rawForecastData.maxTemp)
        htmlDesigner.addHtmlElement("li","Temperatura curenta",rawForecastData.currentTemp)
        htmlDesigner.addHtmlElement("li","Umiditatea",rawForecastData.humidity)
        htmlDesigner.addHtmlElement("</ul>")
        htmlDesigner.addHtmlElement("</h3")

        return htmlDesigner.ProcessHtml()
    }
}