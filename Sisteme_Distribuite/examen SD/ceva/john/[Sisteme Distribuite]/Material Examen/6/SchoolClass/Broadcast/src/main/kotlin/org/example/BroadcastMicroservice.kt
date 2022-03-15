package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread
import kotlinx.coroutines.*

class BroadcastMicroservice {
    private val subscribers : HashMap<Int, Socket>
    private lateinit var broadcastManagerSocket : ServerSocket

    companion object Constants{
        const val BROADCAST_MANAGER_PORT = 1900
    }

    init{
        subscribers = hashMapOf()
    }

    private fun broadcastMessage(message : String){
        subscribers.forEach{
            it.value.getOutputStream().write((message + "\n").toByteArray())
        }
    }

    fun run() = runBlocking{
        broadcastManagerSocket = ServerSocket(BROADCAST_MANAGER_PORT)
        println("BroadcastMicroservice se executa pe portul : ${broadcastManagerSocket.localPort}")
        println("Se asteapta conexiuni si mesaje...")

        while(true){
            val clienConnection = broadcastManagerSocket.accept()

            launch {
                println("Subscriber conectat : ${clienConnection.inetAddress.hostAddress}:${clienConnection.port}")
                synchronized(subscribers){
                    subscribers[clienConnection.port] = clienConnection
                }

                var messageToBroadcast : String = ""
                while(true){
                    print("Introduceti mesajul pe care doriti sa il trimiteti : ")
                    messageToBroadcast = readLine()!!
                    broadcastMessage(messageToBroadcast)
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val messageManagerMicroservice = BroadcastMicroservice()
    messageManagerMicroservice.run()
}