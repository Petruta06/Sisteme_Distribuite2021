package com.sd.laborator

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class StudentMicroservice {
    private lateinit var questionDatabase:MutableList<Pair<String, String>>
    private lateinit var messageManagerSocket:Socket

    init {
        val databaseLines: List<String> = File("/home/ana/Desktop/examen SD/laboratoare/lab8/Cod/p2/kotlin/StudentMicroservice/questions_database.txt").readLines()
        questionDatabase = mutableListOf()
        for (i in 0..(databaseLines.size - 1) step 2) {
            questionDatabase.add(
                Pair(
                    databaseLines[i],
                    databaseLines[i + 1]
                )
            )
        }
    }
        companion object Constants {

            val MESSAGE_MANAGER_HOST =
                System.getenv("MESSAGE_MANAGER_HOST") ?: "localhost"
            const val MESSAGE_MANAGER_PORT = 1500
        }

    private fun subscribeToMessageManager()
    {
        try {
            messageManagerSocket = Socket(MESSAGE_MANAGER_HOST, MESSAGE_MANAGER_PORT)
            println("M-am conectat la MessageManager!")
        }
        catch(e:Exception)
        {
            println("Nu ma pot conecta la MessageManager")
            exitProcess(1)
        }

    }
    private fun respondToQuestion(question:String):String?
    {
        questionDatabase.forEach {
            if(it.first==question)
            {
                return it.second
            }
        }
        return null

    }

    public fun run()
    {
        subscribeToMessageManager()
        println("StudentMicroservice se executa pe portul:${messageManagerSocket.localPort}")
        println("Se asteapta mesaje...")
        val bufferReader =
            BufferedReader(InputStreamReader(messageManagerSocket.inputStream))

        while(true)
        {
            val response = bufferReader.readLine()
            if(response==null)
            {
                println("Microserviciul MessageService " +
                        "${messageManagerSocket.port} a fost oprit.")
                bufferReader.close()
                messageManagerSocket.close()
                break
            }
            thread {
                val (messageType, messageDestination, messageBody) =
                    response.split(" ", limit = 3)
                when(messageType) {
// tipul mesajului cunoscut de acest microserviciu este de forma:
// intrebare <DESTINATIE_RASPUNS> <CONTINUT_INTREBARE>
                            "intrebare" -> {
                    println("Am primit o intrebare de la $messageDestination: \"${messageBody}\"")
                    var responseToQuestion =
                        respondToQuestion(messageBody)
                    responseToQuestion?.let {
                        responseToQuestion = "raspuns $messageDestination $it"
                        println("Trimit raspunsul:\"${response}\"")
                        messageManagerSocket.getOutputStream().write((responseToQuestion +
                                "\n").toByteArray())
                    }
                }
                }
            }
        }
    }
}
fun main(args: Array<String>) {
    val studentMicroservice = StudentMicroservice()
    studentMicroservice.run()
}
