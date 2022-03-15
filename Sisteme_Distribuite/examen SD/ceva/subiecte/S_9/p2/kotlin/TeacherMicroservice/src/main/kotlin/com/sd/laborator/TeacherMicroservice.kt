package com.sd.laborator

import jdk.tools.jaotc.LoadedClass
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class TeacherMicroservice {
    private lateinit var dirijor:Socket
    private lateinit var teacherMicroserviceServerSocket:ServerSocket
    companion object Constants
    {
        val DIRIJOR_HOST = System.getenv("MESSAGE_MANAGER_HOST") ?: "localhost"
        const val DIRIJOR_PORT = 1500
        const val TEACHER_PORT = 1600
    }
    private fun subscribeToMessageManager()
    {
        try
        {
            dirijor = Socket(DIRIJOR_HOST, DIRIJOR_PORT)
            dirijor.soTimeout=3000
            println("M-am conectat la Dirirjor!")

        }
        catch (e:Exception)
        {
            println("Nu ma pot conecta la Dirirjor!")
            exitProcess(1)
        }

    }
    private fun subscribeFriend(localhost:String, port:Int):Socket
    {
        var friend:Socket
        try
        {
            friend = Socket(DIRIJOR_HOST, DIRIJOR_PORT)
            friend.soTimeout=3000
            println("M-am conectat la un prieten!")

        }
        catch (e:Exception)
        {
            println("Nu ma pot conecta la prieten!")
            exitProcess(1)
        }
        return friend
    }
    fun  run()
    {
        subscribeToMessageManager()

        teacherMicroserviceServerSocket = ServerSocket(TEACHER_PORT)
        println("Teacher se executa pe portul  " +
                "${teacherMicroserviceServerSocket.localPort}")
        while(true)
        {
            val clientConnection =
                teacherMicroserviceServerSocket.accept()
            thread {
                println("S-a primit o cerere de la:" +
                        "${clientConnection.inetAddress.hostAddress}:${clientConnection.port}")
// se citeste intrebarea dorita
                val clientBufferReader =
                    BufferedReader(InputStreamReader(clientConnection.inputStream))
                val mess = clientBufferReader.readLine()
                println("Am primit mesajul" + mess)
                if(mess.startsWith("Caut")) {
                    println("Trimit catre dirijor")
                    //inseamna ca trebuie sa caut persoana respectiva si trimit catre dirijor
                    dirijor.getOutputStream().write(mess.toByteArray())
                }
                else {
                    if (mess.startsWith("gasit")) {//am gasit persoana si pot sa ii dau semn
                        println("Trimit catre prieten")
                        var (g, l, localhost, p, port) = mess.split(" ")
                        var friend = subscribeFriend(localhost, port.toInt())
                        friend.getOutputStream().write("Hello!".toByteArray())

                    }
                    else {
                        if(mess.startsWith("exit")) {
                            println("ma deconectez")
                            dirijor.getOutputStream().write("".toByteArray())

                        }
                        else
                        {
                            println("Am primit " + mess)
                        }
                    }
                }
            }
        }
    }
}
fun main(args: Array<String>) {
    val teacherMicroservice = TeacherMicroservice()
    teacherMicroservice.run()
}