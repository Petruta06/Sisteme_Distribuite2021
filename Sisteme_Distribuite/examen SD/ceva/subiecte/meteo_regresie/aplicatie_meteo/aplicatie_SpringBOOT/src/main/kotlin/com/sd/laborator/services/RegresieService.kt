package com.sd.laborator.services

import com.sd.laborator.interfaces.RegresieInterface
import org.springframework.stereotype.Service
import java.io.File

@Service
class RegresieService:RegresieInterface {
    override fun getPanta(): String {
        var result = " "
        var numbers :List<String>
        var f = File("/home/ana/Desktop/examen SD/subiecte" +
                "/S@/aplicatie_meteo/aplicatie_SpringBOOT/data.txt")
        if(f.exists())
        {
          numbers = f.readLines()
            var suma = 0
            for(nr in numbers)
            {
                suma = suma +nr.toInt()
            }
            result = suma.toString()
        }
        return result
    }
}