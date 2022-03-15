package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.net.URI

data class Produse(val produse_medicale : List<String>){
    override fun toString(): String {
        var products : String = "";
        produse_medicale.forEach{produs -> products += produs + "\n"}
        return products;
    }

    val Produse : List<String>
        get() = this.produse_medicale;
}

data class Client(val identitateClient : String, val adresaLivrare : String,
                  val cantitate : String, val produs : String)

@EnableBinding(Source::class)
@SpringBootApplication
class ClientMicroservice {
    private lateinit var listaProduse : List<String>
    private lateinit var listaClienti : List<Client>
    init{
        var mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())
        var jsonString: String = File("databases/listaProduse.json").readText(Charsets.UTF_8)
        val Produse = mapper.readValue<Produse>(jsonString)
        listaProduse = Produse.Produse;

        jsonString = File("databases/listaClienti.json").readText(Charsets.UTF_8)
        listaClienti = mapper.readValue<List<Client>>(jsonString)
    }
    companion object{
        var contor = 0;
    }

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = [Poller(fixedDelay = "10000", maxMessagesPerPoll = "1")])
    fun comandaProdus(): () -> Message<String> {
        return {
            contor = contor + 1;
            if(contor == listaClienti.size)
                contor = 0
            val client : Client = listaClienti[contor]
            var produsComandat : String = ""
            if(client.produs in listaProduse == false)
                produsComandat = client.produs;
            else
                produsComandat = listaProduse[(0 until listaProduse.size).shuffled()[0]]

            val mesaj = "${client.identitateClient}|$produsComandat|${client.cantitate}|${client.adresaLivrare}"
            println("\n\n" + mesaj + "\n\n")
            MessageBuilder.withPayload(mesaj).build()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ClientMicroservice>(*args)
}