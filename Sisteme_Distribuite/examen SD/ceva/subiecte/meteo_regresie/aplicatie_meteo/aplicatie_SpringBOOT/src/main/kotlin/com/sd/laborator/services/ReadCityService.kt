package com.sd.laborator.services

import com.sd.laborator.interfaces.ReadCityInterface
import org.springframework.stereotype.Service
import java.io.File

@Service
class ReadCityService:ReadCityInterface {
    override fun getCity(): List<String> {
        var result = listOf<String>()
        val f = File("/home/ana/Desktop/examen SD/subiecte" +
                "/S@/aplicatie_meteo/aplicatie_SpringBOOT/cities.txt")
        if(f.exists())
        {
             result =f.readLines()
        }
        return result
    }
}