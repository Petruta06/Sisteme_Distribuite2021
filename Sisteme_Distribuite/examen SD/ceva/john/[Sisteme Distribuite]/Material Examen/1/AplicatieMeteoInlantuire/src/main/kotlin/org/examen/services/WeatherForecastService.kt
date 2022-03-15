package org.examen.services

import org.examen.interfaces.LocationSearchInterface
import org.examen.interfaces.WeatherForecastInterface
import org.examen.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt

@Service
class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface{
    @Autowired
    private lateinit var locationSearchService : LocationSearchInterface

    override fun getForecastData(location : String): WeatherForecastData? {
        var locationId = locationSearchService.getLocationId(location)

        if(locationId == -1){
            return null
        }

        val forecastDataURL = URL("https://www.metaweather.com/api/location/$locationId/")

        val rawResponse : String = forecastDataURL.readText()

        val responseRootObject = JSONObject(rawResponse)
        val weatherDataObject  = responseRootObject.getJSONArray("consolidated_weather").getJSONObject(0)

        return WeatherForecastData(
            location = responseRootObject.getString("title"),
            date = timeService.getCurrentTime(),
            weatherState = weatherDataObject.getString("weather_state_name"),
            weatherStateIconURL = "https://www.metaweather.com/static/img/weather/png/${weatherDataObject.getString("weather_state_abbr")}.png",
            windDirection = weatherDataObject.getString("wind_direction_compass"),
            windSpeed = weatherDataObject.getFloat("wind_speed").roundToInt(),
            minTemp = weatherDataObject.getFloat("min_temp").roundToInt(),
            maxTemp = weatherDataObject.getFloat("max_temp").roundToInt(),
            currentTemp = weatherDataObject.getFloat("the_temp").roundToInt(),
            humidity = weatherDataObject.getFloat("humidity").roundToInt()
        )
    }
}