package org.examen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherApp
    fun main(args : Array<String>){
        runApplication<WeatherApp>()
    }