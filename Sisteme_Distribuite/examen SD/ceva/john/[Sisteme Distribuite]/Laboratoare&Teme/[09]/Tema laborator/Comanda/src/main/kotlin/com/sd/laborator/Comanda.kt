package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import org.springframework.messaging.support.MessageBuilder
import java.io.File
import java.io.IOError
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.DateFormat
import java.text.SimpleDateFormat
import kotlin.random.Random

@EnableBinding(Processor::class)
@SpringBootApplication
class ComandaMicroservice {
    companion object{
        var nrInregistrare = 0
    }
    private fun pregatireComanda(produs: String, cantitate: Int): Int {
        println("Se pregateste comanda $cantitate x \"$produs\"...")
        val command : String = "${nrInregistrare}|${produs}|${cantitate}\n"
        try {
            Files.write(Paths.get("databases/comenzi.txt"), command.toByteArray(), StandardOpenOption.APPEND)
            return (nrInregistrare++);
        }
        catch (e: IOException){
            println("Unable to write command!")
        }
        return -1
    }

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    fun preluareComanda(comanda: String?): String {
        val (identitateClient, produsComandat, cantitate, adresaLivrare) = comanda!!.split("|")
        println("Am primit comanda urmatoare: ")
        println("$identitateClient | $produsComandat | $cantitate | $adresaLivrare")

        val idComanda = pregatireComanda(produsComandat, cantitate.toInt())
        Files.write(Paths.get("databases/listaComenzi.txt"), (idComanda.toString() + "|" + comanda + "\n").toByteArray(), StandardOpenOption.APPEND)

        return "$idComanda"
    }
}

fun main(args: Array<String>) {
    runApplication<ComandaMicroservice>(*args)
}