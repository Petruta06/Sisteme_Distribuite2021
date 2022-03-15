package com.sd.laborator

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class MessageManagerMicroservice {
    private val subscribers:HashMap<Int, Socket>
    private lateinit var messageManagerSocket: ServerSocket
    private lateinit var heartbeat:Socket

    companion object Constants
    {
        const val MESSAGE_MANAGER_PORT = 1500
        const val HEARTBEAT_PORT = 3500
        val HEARTBEAT_HOST =
            System.getenv("MESSAGE_MANAGER_HOST") ?: "localhost"
    }
    init {
        subscribers= hashMapOf()
    }
    private fun subscribeToHeartBeat()
    {
        try {
            heartbeat = Socket(HEARTBEAT_HOST, HEARTBEAT_PORT)
            println("M-am conectat la Hearbeat!")
        }
        catch(e:Exception)
        {
            println("Nu ma pot conecta la Hearbeat")
            exitProcess(1)
        }

    }
    private fun broadcastMessage(message:String, except:Int)
    {
        subscribers.forEach {
            it.takeIf {
                it.key!=except
            }?.value?.getOutputStream()?.write((
                    message+"\n"
                    ).toByteArray())
        }

    }
private fun respondTo(destination:Int, message: String)
{
    subscribers[destination]?.getOutputStream()?.write((message+"\n").toByteArray())
}
    private fun responseHeartBeat()
    {
        val bufferReader =
            BufferedReader(InputStreamReader(heartbeat.inputStream))
        while(true)
        {
            val response = bufferReader.readLine()
            if(response==null)
            {
                println("hearbeat inchis")
                bufferReader.close()
                heartbeat.close()
                break

            }
            else
            {
                var state ="inca in viata!"
               heartbeat.getOutputStream().write((state +
                        "\n").toByteArray())

            }
        }

    }
    public fun run()
    {
        subscribeToHeartBeat()
        responseHeartBeat()
        messageManagerSocket = ServerSocket(MESSAGE_MANAGER_PORT)
        println("MessageManagerMicroservice se executa pe portul:${messageManagerSocket.localPort}")
        println("Se asteapta conexiuni si mesaje...")
        while (true) {
// se asteapta conexiuni din partea clientilor subscriberi
            val clientConnection = messageManagerSocket.accept()
// se porneste un thread separat pentru tratare
            thread {
                println("Subscriber conectat: ${clientConnection.inetAddress.hostAddress}:${clientConnection.port}")
                synchronized(subscribers) {
                    subscribers[clientConnection.port] =
                        clientConnection
                }
                val bufferReader =
                    BufferedReader(InputStreamReader(clientConnection.inputStream))
                while (true) {
// se citeste raspunsul de pe socketul TCP
                    val receivedMessage = bufferReader.readLine()
// daca se primeste un mesaj gol (NULL), atunci inseamna ca cealalta parte a socket-ului a fost inchisa
                            if (receivedMessage == null) {
// deci subscriber-ul respectiv a fost deconectat
                                println("Subscriber-ul ${clientConnection.port} a fost deconectat.")
                                synchronized(subscribers) {
                                    subscribers.remove(clientConnection.port)
                                }
                                bufferReader.close()
                                clientConnection.close()
                                break
                            }
                    println("Primit mesaj: $receivedMessage")
                    val (messageType, messageDestination, messageBody)
                            = receivedMessage.split(" ", limit = 3)

                    println(messageType)
                    when (messageType) {
                        "intrebare" -> {
                            println("Trimit intrebare")
                            broadcastMessage(
                                "intrebare ${clientConnection.port} $messageBody",
                                except = clientConnection.port
                            )
                        }
                        "raspuns" -> {
                            println("Trimit raspuns")
                            respondTo(
                                messageDestination.toInt(),
                                messageBody
                            )
                        }
                        "alive" -> {
                            println("raspund la dummy")
                            respondTo(
                                messageDestination.toInt(),
                                messageBody
                            )
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

