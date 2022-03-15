package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.system.exitProcess
import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MessageManagerMicroservice {
    private val subscribers : HashMap<Int, Socket>
    private lateinit var messageManagerSocket : ServerSocket
    private lateinit var broadcastManagerSocket : Socket


    companion object Constants{
        const val MESSAGE_MANAGER_PORT = 1500
        const val BROADCAST_MANAGER_PORT = 1900
        const val BROADCAST_MANAGE_HOST = "localhost"
    }

    init{
        subscribers = hashMapOf()
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

    private fun broadcastMessage(message : String, except : Int){
        subscribers.forEach{
            if(it.key != except){
                it.value.getOutputStream().write((message + "\n").toByteArray())
            }
        }
    }

    private fun respondTo(destination : Int, message : String){
        subscribers[destination]?.getOutputStream()?.write((message + "\n").toByteArray())
    }

    fun run() = runBlocking{
        subscribeToBroadcastManager()
        messageManagerSocket = ServerSocket(MESSAGE_MANAGER_PORT)
        println("MessageManagerMicroservice se executa pe portul : ${messageManagerSocket.localPort}")
        println("Se asteapta conexiuni si mesaje...")

        while(true){
            launch{
                val bufferedbroadcastReader = BufferedReader(InputStreamReader(broadcastManagerSocket.getInputStream()))

                val broadcastMessage = bufferedbroadcastReader.readLine()
                if (broadcastMessage == null) {
                    println("Microserviciul BroadcastService  ${broadcastManagerSocket.localPort}  a fost oprit.")
                    bufferedbroadcastReader.close()
                    broadcastManagerSocket.close()
                    exitProcess(1)
                }
                else {
                    println("Am primit mesajul de broadcast de la BroadcastMicroservice : ${broadcastMessage}")
                }
            }
            launch{
                val clienConnection = messageManagerSocket.accept()

                thread {
                    println("Subscriber conectat : ${clienConnection.inetAddress.hostAddress}:${clienConnection.port}")
                    synchronized(subscribers) {
                        subscribers[clienConnection.port] = clienConnection
                    }

                    val bufferedReader = BufferedReader(InputStreamReader(clienConnection.getInputStream()))

                    while (true) {
                        val receivedMessage = bufferedReader.readLine()
                        if (receivedMessage == null) {
                            println("Subscriberul ${clienConnection.port} a fost deconectat!")
                            synchronized(subscribers) {
                                subscribers.remove(clienConnection.port)
                            }
                            bufferedReader.close()
                            clienConnection.close()
                            break
                        }
                        println("Primit mesaj : $receivedMessage")
                        val (messageType, messageDestination, messageBody) = receivedMessage.split(" ", limit = 3)
                        when (messageType) {
                            // intrebare <DESTINATIE_RASPUNS> <CONTINUT_INTREBARE>
                            "intrebare" -> {
                                broadcastMessage("intrebare ${clienConnection.port} $messageBody", clienConnection.port)
                            }
                            // raspuns <CONTINUT_RASPUNS>
                            "raspuns" -> {
                                respondTo(messageDestination.toInt(), messageBody)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val messageManagerMicroservice = MessageManagerMicroservice()
    messageManagerMicroservice.run()
}