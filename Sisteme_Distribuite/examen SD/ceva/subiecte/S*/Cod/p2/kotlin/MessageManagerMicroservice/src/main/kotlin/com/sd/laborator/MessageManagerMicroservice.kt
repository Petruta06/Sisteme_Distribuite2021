package com.sd.laborator

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class MessageManagerMicroservice {
    private val subscribers:HashMap<Int, Socket>
    private lateinit var messageManagerSocket: ServerSocket
    private lateinit var managerSocket: Socket

    companion object Constants
    {
        const val MESSAGE_MANAGER_PORT = 1500
        const val MANAGER_PORT = 1234
        const val MANAGER_HOST = "localhost"
    }
    init {
        subscribers= hashMapOf()
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
    fun run()
    {
        messageManagerSocket = ServerSocket(MESSAGE_MANAGER_PORT)
        managerSocket = Socket(MANAGER_HOST, MANAGER_PORT)
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
                val buffManager = BufferedReader(InputStreamReader(managerSocket.inputStream))
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
                    var (messageType, messageDestination, messageBody)
                            = receivedMessage.split(" ", limit = 3)
                    println("Trimit mesajul ${messageBody} catre manager")
                    managerSocket.getOutputStream().write(messageBody.toByteArray())
                    println("Astept raspuns de la manager")
                    messageBody = buffManager.readLine()
                    println(messageType)
                    when (messageType) {
                        "intrebare" -> {
                            println("Trimit intrebare")
        broadcastMessage("intrebare ${clientConnection.port} $messageBody", except = clientConnection.port)
                        }
                        "raspuns" -> {
                            println("Trimit raspuns")
                            respondTo(messageDestination.toInt(),
                                messageBody)
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

