package com.sd.laborator

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.*
import kotlin.system.exitProcess
import kotlinx.coroutines.*


class TeacherMicroservice {
    private lateinit var messageManagerSocket: Socket
    private lateinit var teacherMicroserviceServerSocket: ServerSocket

    companion object Constants {
        val MESSAGE_MANAGER_HOST = System.getenv("MESSAGE_MANAGER_HOST") ?: "localhost"
        const val MESSAGE_MANAGER_PORT = 1500
        const val TEACHER_PORT = 1600
    }

    private fun subscribeToMessageManager() {
        try {
            messageManagerSocket = Socket(MESSAGE_MANAGER_HOST, MESSAGE_MANAGER_PORT)
            messageManagerSocket.soTimeout = 3000
            println("M-am conectat la MessageManager!")
        } catch (e: Exception) {
            println("Nu ma pot conecta la MessageManager!")
            exitProcess(1)
        }
    }

     fun run(){
        subscribeToMessageManager()

        teacherMicroserviceServerSocket = ServerSocket(TEACHER_PORT)

        println("TeacherMicroservice se executa pe portul: ${teacherMicroserviceServerSocket.localPort}")
        println("Se asteapta cereri (intrebari)...")

        while (true) {
            val clientConnection = teacherMicroserviceServerSocket.accept()

            GlobalScope.async {
                println("S-a primit o cerere de la: ${clientConnection.inetAddress.hostAddress}:${clientConnection.port}")

                val clientBufferReader = BufferedReader(InputStreamReader(clientConnection.inputStream))
                val receivedQuestion = clientBufferReader.readLine()

                println("Trimit catre MessageManager: ${"intrebare ${messageManagerSocket.localPort} $receivedQuestion\n"}")
                messageManagerSocket.getOutputStream().write(("intrebare ${messageManagerSocket.localPort} $receivedQuestion\n").toByteArray())

                val messageManagerBufferReader = BufferedReader(InputStreamReader(messageManagerSocket.inputStream))
                try {
                    withTimeout(3000L) {
                        val receivedResponse = messageManagerBufferReader.readLine()
                        println("Am primit raspunsul: \"$receivedResponse\"")
                        clientConnection.getOutputStream().write((receivedResponse + "\n").toByteArray())
                    }
                } catch (e: SocketTimeoutException) {
                    println("Nu a venit niciun raspuns in timp util.")
                    clientConnection.getOutputStream().write("Nu a raspuns nimeni la intrebare\n".toByteArray())
                } catch(e : TimeoutCancellationException){
                    println("MessageManager nu a raspuns la timp!")
                }
                finally {
                    clientConnection.close()
                }
            }
        }
    }
}

fun main(args: Array<String>){
    val teacherMicroservice = TeacherMicroservice()
    teacherMicroservice.run()
}