package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.random.Random

@EnableBinding(Processor::class)
@SpringBootApplication
class FacturareMicroservice {
    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    fun emitereFactura(comanda: String?): String {
        val identificatorComanda = comanda!!.toInt()
        println("Emit factura pentru comanda $comanda...")

        val commandsFileLines : List<String> = File("databases/listaComenzi.txt").readLines()
        Files.write(Paths.get("databases/listaComenzi.txt"), "".toByteArray(), StandardOpenOption.WRITE)
        var identitate : String = ""
        var adresa :  String = ""
        for(line in commandsFileLines){
            val currentContent = line.split("|")
            if(currentContent[0].toInt() == identificatorComanda){
                identitate = currentContent[1]
                adresa = currentContent[4]
            }
            else{
                Files.write(Paths.get("databases/listaComenzi.txt"), (line + "\n").toByteArray(), StandardOpenOption.APPEND)
            }
        }

        println("S-a emis factura cu nr $identificatorComanda.")
        val comand : String = "${comanda}|${identitate}|${adresa}\n"
        Files.write(Paths.get("databases/listaFacturi.txt"), comand.toByteArray(), StandardOpenOption.APPEND)

        return "$comanda"
    }
}

fun main(args: Array<String>) {
    runApplication<FacturareMicroservice>(*args)
}