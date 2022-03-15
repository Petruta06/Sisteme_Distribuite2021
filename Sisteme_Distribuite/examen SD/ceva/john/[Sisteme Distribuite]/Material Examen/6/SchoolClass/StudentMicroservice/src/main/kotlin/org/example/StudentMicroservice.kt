package org.example

import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Exception
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.system.exitProcess
import kotlinx.coroutines.*

class StudentMicroservice {
    // [<intrebare, raspuns>, <intrebare, raspuns> ... ]
    private lateinit var questionDatabase : MutableList<Pair<String, String>>

    private lateinit var messageManagerSocket : Socket
    private lateinit var broadcastManagerSocket : Socket

    init{
        val databaseLines : List<String> = File("questions_database.txt").readLines()
        questionDatabase = mutableListOf()

        /* <Intrebare>
            <Raspuns>
            <Intrebare>
            <Raspuns>
         */

        for(i in 0..(databaseLines.size - 1) step 2){
            questionDatabase.add(Pair(databaseLines[i], databaseLines[i+1]))
        }
    }

    companion object Constants{
        const val MESSAGE_MANAGER_HOST = "localhost"
        const val BROADCAST_MANAGE_HOST = "localhost"
        const val MESSAGE_MANAGER_PORT = 1500
        const val BROADCAST_MANAGER_PORT = 1900
    }

    private fun subscribeToMessageManager(){
        try{
            messageManagerSocket = Socket(MESSAGE_MANAGER_HOST, MESSAGE_MANAGER_PORT)
            println("M-am conectat la MessageManager!")
        }
        catch (e : Exception){
            println("Nu ma pot conecta la MessageManager!")
            exitProcess(1)
        }
    }

    private fun subscribeToBroadcastManager(){
        try{
            broadcastManagerSocket = Socket(BROADCAST_MANAGE_HOST, BROADCAST_MANAGER_PORT)
            println("M-am conectat la BroadcastManager!")
        }
        catch (e : Exception){
            println("Nu ma pot conecta la BroadcastManager!")
            exitProcess(1)
        }
    }

    private fun respondToQuestion(question : String) : String?{
        questionDatabase.forEach{
            if(it.first == question){
                return it.second
            }
        }
        return null
    }

    fun run()  = runBlocking{
        subscribeToMessageManager()
        subscribeToBroadcastManager()
        println("StudentMicroservice se executa pe portul : ${messageManagerSocket.localPort}")
        println("Se asteapta intrebari...")

        val bufferedReader = BufferedReader(InputStreamReader(messageManagerSocket.getInputStream()))
        val bufferedbroadcastReader = BufferedReader(InputStreamReader(broadcastManagerSocket.getInputStream()))

        while(true){
            launch {
                val response = bufferedReader.readLine()
                if (response == null) {
                    println("Microserviciul MessageService  ${messageManagerSocket.localPort}  a fost oprit.")
                    bufferedReader.close()
                    messageManagerSocket.close()
                    exitProcess(1)
                }
                else {
                    thread {
                        val (messageType, messageDestination, messageBody) = response.split(" ", limit = 3)
                        when (messageType) {
                            // intrebare <Destinatie_raspuns> <continut_intrebare>
                            "intrebare" -> {
                                println("Am primit o intrebare de la $messageDestination : \"${messageBody}\"")
                                var responseToQuestion = respondToQuestion(messageBody)
                                responseToQuestion?.let {
                                    responseToQuestion = "raspuns $messageDestination $it"
                                    println("Trimit raspunsul : \"$response\"")

                                    messageManagerSocket.getOutputStream()
                                        .write((responseToQuestion + "\n").toByteArray())
                                }
                            }
                        }
                    }
                }
            }
            launch{
                val broadcastMessage = bufferedbroadcastReader.readLine()
                if (broadcastMessage == null) {
                    println("Microserviciul BroadcastService  ${broadcastManagerSocket.localPort}  a fost oprit.")
                    bufferedbroadcastReader.close()
                    broadcastManagerSocket.close()
                    return@launch
                }
                else {
                    println("Am primit mesajul de broadcast de la BroadcastMicroservice : ${broadcastMessage}")
                }
            }
        }
    }
}

fun main(args : Array<String>){
    val studentMicroservice : StudentMicroservice = StudentMicroservice()
    studentMicroservice.run()
}