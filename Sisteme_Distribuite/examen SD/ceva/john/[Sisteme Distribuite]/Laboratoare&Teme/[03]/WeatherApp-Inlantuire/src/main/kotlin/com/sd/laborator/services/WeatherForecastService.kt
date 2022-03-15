package com.sd.laborator.services

import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt

@Service
class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface {
    @Autowired
    private lateinit var locationSearchService : LocationSearchInterface
    override fun getForecastData(locationName: String): WeatherForecastData {
        val locationId = locationSearchService.getLocationId(locationName)

        val forecastDataURL = URL("https://www.metaweather.com/api/location/$locationId/")

        val rawResponse: String = forecastDataURL.readText()


        val responseRootObject = JSONObject(rawResponse)
        val weatherDataObject = responseRootObject.getJSONArray("consolidated_weather").getJSONObject(0)


        return WeatherForecastData(
            location = responseRootObject.getString("title"),
            date = timeService.getCurrentTime(),
            weatherState = weatherDataObject.getString("weather_state_name"),
            weatherStateIconURL =
                "https://www.metaweather.com/static/img/weather/png/${weatherDataObject.getString("weather_state_abbr")}.png",
            windDirection = weatherDataObject.getString("wind_direction_compass"),
            windSpeed = weatherDataObject.getFloat("wind_speed").roundToInt(),
            minTemp = weatherDataObject.getFloat("min_temp").roundToInt(),
            maxTemp = weatherDataObject.getFloat("max_temp").roundToInt(),
            currentTemp = weatherDataObject.getFloat("the_temp").roundToInt(),
            humidity = weatherDataObject.getFloat("humidity").roundToInt()
        )
    }

}