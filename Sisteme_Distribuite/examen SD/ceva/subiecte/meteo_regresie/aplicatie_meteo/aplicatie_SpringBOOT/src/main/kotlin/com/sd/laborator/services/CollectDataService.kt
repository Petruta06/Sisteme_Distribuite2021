package com.sd.laborator.services

import com.sd.laborator.interfaces.CollectDataInterface
import org.springframework.stereotype.Service
import java.io.File
@Service
class CollectDataService:CollectDataInterface {
    override fun putInFile(s: String) {
        var f = File("/home/ana/Desktop/examen SD/subiecte" +
                "/S@/aplicatie_meteo/aplicatie_SpringBOOT/data.txt")
        if(!f.exists())
        {
            f.createNewFile()
        }
        var date = s.split(" ")
        var i =0
        for( c in date)
        {
            if(c.matches("-?\\d+(\\.\\d+)?".toRegex())==true)
            {
                f.appendText(c)
            }
        }

    }
}