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
import java.io.File
import java.lang.StringBuilder
import java.lang.reflect.Field
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

@EnableBinding(Source::class)
@SpringBootApplication
class SpringDataFlowTimeSourceApplication {

    private fun getCommand(fileName : String) : String?{
        var commands : List<String> = File(fileName).readLines()
        if(commands.size == 0){
            return null
        }
        val currentCommand = commands[0]
        Files.write(Paths.get(fileName), "".toByteArray(), StandardOpenOption.WRITE)
        File(fileName).writeText("")
        for(command in commands.subList(1, commands.size)){
            Files.write(Paths.get(fileName), (command + "\n").toByteArray(), StandardOpenOption.APPEND)
        }
        return currentCommand
    }
    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = [Poller(fixedDelay = "10000", maxMessagesPerPoll = "1")])
    fun timeMessageSource(): () -> Message<String> {
        return {
            val command : String? = getCommand("comenzi.txt")
            println("Am trimis comanda : ${command}")
            MessageBuilder.withPayload(command!!).build()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringDataFlowTimeSourceApplication>(*args)
}