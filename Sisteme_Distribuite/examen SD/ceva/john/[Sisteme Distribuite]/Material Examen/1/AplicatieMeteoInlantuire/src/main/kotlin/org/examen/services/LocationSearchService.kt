package org.examen.services

import org.examen.interfaces.LocationSearchInterface
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class LocationSearchService : LocationSearchInterface{
    override fun getLocationId(locationName: String): Int {
        // codificare parametru URL, deoarece poate contine caractere speciale
        val encodedLocationName = URLEncoder.encode(locationName, StandardCharsets.UTF_8.toString())

        // construire obiect de tip URL
        val locationSearchURL = URL("https://www.metaweather.com/api/location/search/?query=$encodedLocationName")

        // preluare raspuns HTTP (se face cerere GET si se preia continutul raspunsului sub forma de text)
        val rawResponse : String = locationSearchURL.readText()

        // parsare obiect JSON
        val responseRootObject = JSONObject("{\"data\" : ${rawResponse}}")
        val responseContentObject = responseRootObject.getJSONArray("data").takeUnless { it.isEmpty }?.getJSONObject(0)

        return responseContentObject?.getInt("woeid") ?: -1
    }
}