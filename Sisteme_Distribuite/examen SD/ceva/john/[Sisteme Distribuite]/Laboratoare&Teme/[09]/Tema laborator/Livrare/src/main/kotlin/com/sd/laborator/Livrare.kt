package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@EnableBinding(Sink::class)
@SpringBootApplication
class LivrareMicroservice {
    @StreamListener(Sink.INPUT)
    fun expediereComanda(comanda: String) {
        val identificatorComanda = comanda.toInt()
        val facturiFileLines : List<String> = File("databases/listaFacturi.txt").readLines()

        Files.write(Paths.get("databases/listaComenzi.txt"), "".toByteArray(), StandardOpenOption.WRITE)
        var identitate : String = ""
        var adresa :  String = ""
        for(line in facturiFileLines){
            val currentContent = line.split("|")
            if(currentContent[0].toInt() == identificatorComanda){
                identitate = currentContent[1]
                adresa = currentContent[4]
            }
            else{
                Files.write(Paths.get("databases/listaFacturi.txt"), (line + "\n").toByteArray(), StandardOpenOption.APPEND)
            }
        }
        val comand : String = "${comanda}|${identitate}|${adresa}\n"
        println("S-a expediat urmatoarea comanda: $comand")
    }
}

fun main(args: Array<String>) {
    runApplication<LivrareMicroservice>(*args)
}