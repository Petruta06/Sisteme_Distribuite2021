package com.sd.laborator

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class HearbeatMicroservice {
    private val subscribers: HashMap<Int, Socket>

    private lateinit var socketServer: ServerSocket

    companion object Constants {
        const val HEARTBEAT_PORT = 3500
    }

    init {
        subscribers = hashMapOf()
    }

    //trimit mesaj pentru fiecare entitate din subscribe
    private fun sendMessage() {
        var message = "alive ${socketServer.localPort} alive?"
        println("Trimit ${message}")
        subscribers.forEach {
            println("am trimis o data!")
            it.value.getOutputStream()?.write((message + "\n").toByteArray())
        }

    }
    public fun run() {
        var last_dummy = Date()
        socketServer = ServerSocket(HEARTBEAT_PORT)
        println("Heartbeat se executa pe portul ${socketServer.localPort} ")
        println("astept subscriberi")

        while (true) {
            // astept conexiunii
            val client = socketServer.accept()
            //pornesc un thread care sa imi prelucreze inf despre clientii
            thread {
                println(
                    "Subscriber conectat: ${client.inetAddress.hostAddress}:" +
                            "${client.port}"
                )
                synchronized(subscribers) {
                    subscribers[client.port] = client
                }
                val bufferReader =
                    BufferedReader(InputStreamReader(client.inputStream))
                while(true)
                {
                    println("in while true")
                    var now = Date()
                    println(now.time-last_dummy.time)
                    //verific ca am cui sa trimit mesaje dummy si verific ca trecut ceva timp
                    if (!subscribers.isEmpty() && (now.time-last_dummy.time>10) ) {
                        last_dummy = now
                        sendMessage()
                    }
                    val receive = bufferReader.readLine()
                    if(receive==null)
                    {
                        println("Subscriber-ul ${client.port} nu mai raspunde.")
                        synchronized(subscribers) {
                            subscribers.remove(client.port)
                        }
                        bufferReader.close()
                        client.close()
                        break
                    }
                    println("Subscriber-ul ${client.port} a raspuns.")
                }
            }
                /*daca am subscriberi incep sa trimit mesaje
                var now = Date()
                //verific ca am cui sa trimit mesaje dummy si verific ca trecut ceva timp
                if (!subscribers.isEmpty() && (now.time-last_dummy.time>10) ) {
                    last_dummy = now
                    thread { sendMessage() }
                }*/
        }

    }
}

fun main(args: Array<String>) {
    val hearbeatMicroservice = HearbeatMicroservice()
    hearbeatMicroservice.run()
}