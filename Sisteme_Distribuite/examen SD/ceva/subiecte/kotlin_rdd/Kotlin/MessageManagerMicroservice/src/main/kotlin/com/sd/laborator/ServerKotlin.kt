package com.sd.laborator

import java.net.ServerSocket
import java.net.Socket
import kotlin.collections.set
import kotlin.concurrent.thread
class ServerKotlin {

    private lateinit var kotlinSocket: ServerSocket
    private val clients:HashMap<Int, Socket>
    private val inf = DateInformation()

    companion object Constants
    {
        const val KOTLIN_PORT = 1500
    }

    init {
        clients = hashMapOf()
    }
    private fun send(destination:Int, message: String)
    {
       clients[destination]?.getOutputStream()?.write((message+"\n").toByteArray())
    }

    fun run()
    {
       kotlinSocket = ServerSocket(KOTLIN_PORT)
        println("Serverul se executa pe portul:${kotlinSocket.localPort}")
        println("Se asteapta conexiuni si mesaje...")
        while (true) {
// se asteapta conexiuni din partea clientilor subscriberi
            val clientConnection =  kotlinSocket.accept()
// se porneste un thread separat pentru tratare
            thread {
                println("Subscriber conectat: ${clientConnection.inetAddress.hostAddress}:${clientConnection.port}")
                synchronized(clients) {
                    clients[clientConnection.port] =
                        clientConnection
                }
                //extrag stirile si le trimit pe socket

                thread {
                    while(true)
                    {
                        send(clientConnection.port,
                            inf.getInformation("https://khttp.readthedocs.io/en/latest/"))
                        Thread.sleep(3000)
                    }

                }
                }
            }
        }
    }



fun main(args: Array<String>) {
    val serverKotlin = ServerKotlin()
    serverKotlin.run()
}