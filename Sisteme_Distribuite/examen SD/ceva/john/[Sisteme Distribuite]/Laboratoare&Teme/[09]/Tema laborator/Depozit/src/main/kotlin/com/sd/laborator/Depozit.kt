package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import org.springframework.messaging.support.MessageBuilder
import kotlin.random.Random

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

data class Produs(var produs : String, var cantitate: String)

@EnableBinding(Processor::class)
@SpringBootApplication
class DepozitMicroservice {
    private lateinit var stocProduse : List<Produs>
    init{
        var mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())
        var jsonString: String = File("stocuri.json").readText(Charsets.UTF_8)
        stocProduse = mapper.readValue<List<Produs>>(jsonString)
    }

    private fun acceptareComanda(identificator: Int, produs : String, cantitate: Int): String {
        println("Comanda cu identificatorul $identificator a fost acceptata!")

        return pregatireColet(produs, cantitate)
    }

    private fun respingereComanda(identificator: Int): String {
        println("Comanda cu identificatorul $identificator a fost respinsa! Stoc insuficient.")
        return "RESPINSA"
    }

    private fun verificareStoc(produs: String, cantitate: Int): Boolean {
        for(product in stocProduse){
            if(product.produs.equals(produs)){
                if(product.cantitate.toInt() > cantitate){
                    return true;
                }
            }
        }
        return false
    }

    private fun pregatireColet(produs: String, cantitate: Int): String {
       for(product in stocProduse){
            if(product.produs.equals(produs)){
                var quantity = product.cantitate.toInt()
                if(quantity > cantitate){
                    quantity -= cantitate;
                    product.cantitate = quantity.toString()
                }
            }
        }
        println("Produsul $produs in cantitate de $cantitate buc. este pregatit de livrare.")
        return "$produs|$cantitate"
    }

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    fun procesareComanda(comanda: String?): String {
        val identificatorComanda = comanda!!.toInt()
        println("Procesez comanda cu identificatorul $identificatorComanda...")

        val depositFileLines : List<String> = File("databases/comenzi.txt").readLines()
        Files.write(Paths.get("databases/comenzi.txt"), "".toByteArray(), StandardOpenOption.WRITE)
        var produs : String = ""
        var cantitate: Int = 0
        for(line in depositFileLines){
            val currentContent = line.split("|")
            if(currentContent[0].toInt() == identificatorComanda){
                produs = currentContent[1]
                cantitate = currentContent[2].toInt()
            }
            else{
                Files.write(Paths.get("databases/comenzi.txt"), (line + "\n").toByteArray(), StandardOpenOption.APPEND)
            }
        }
        val rezultatProcesareComanda: String = if (verificareStoc(produs, cantitate)) {
            acceptareComanda(identificatorComanda, produs, cantitate)
        } else {
            respingereComanda(identificatorComanda)
        }
        return "${identificatorComanda}"
    }
}

fun main(args: Array<String>) {
    runApplication<DepozitMicroservice>(*args)
}