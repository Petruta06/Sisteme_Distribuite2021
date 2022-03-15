package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.DateFormat
import java.text.SimpleDateFormat

@EnableBinding(Processor::class)
@SpringBootApplication
class SpringDataFlowTimeProcessorApplication {
    companion object{
        val bufferFile : String = "buffer.txt"
        val processBuilder : ProcessBuilder = ProcessBuilder()
    }

    private fun executeCommand(command : String, index : Int = 1): Boolean{
        if(index == 1)
            processBuilder.command("bash", "-c", command + " " + bufferFile)
        else if(index == 0)
            processBuilder.command("bash", "-c", command)
        try{
            val process : Process = processBuilder.start()
            val output : StringBuilder = StringBuilder()
            val bufferedReader : BufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            var string : String?
            while(true){
                string = bufferedReader.readLine()
                if(string == null)
                    break
                output.append(string + "\n")
            }
            println("Rezultatul executiei : ${output.toString()}")
            Files.write(Paths.get(bufferFile), output.toString().toByteArray(), StandardOpenOption.WRITE)
        }
        catch (e : IOException){
            println("IOException")
            return false
        }
        return true
    }

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    fun transform(message: String): Any? {
        val messageReceived = message.split('|')
        val pipePosition = message.indexOf('|')
        val currentCommand : String = messageReceived[0]
        var commandExecution : Boolean
        if(messageReceived.size == 3)
            executeCommand(currentCommand, 0)
        else
            executeCommand(currentCommand)

        if(pipePosition == -1) {
            print("Am ajuns la finalul executiei!")
            return "end"
        }
        println("Trimitem restul comenzii!")
        return message.substring(pipePosition + 1)
    }
}

fun main(args: Array<String>) {
    runApplication<SpringDataFlowTimeProcessorApplication>(*args)
}