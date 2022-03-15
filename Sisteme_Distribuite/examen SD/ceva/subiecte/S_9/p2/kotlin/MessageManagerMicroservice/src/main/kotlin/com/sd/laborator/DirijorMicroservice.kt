package com.sd.laborator

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

data class Person(var nume:String, val port:Int, var adress: InetAddress)
{

}

class DirijorMicrosevice {
    private lateinit var persons:HashMap<Int, Person>
    private lateinit var server:ServerSocket
    private var indice = 0

    companion object Constants
    {
        const val SERVER_PORT = 1500
        val lista = listOf<String>("Bogdan", "Maria", "Ioana")
    }

    init {
        persons = hashMapOf()
    }

    private fun handleMessage(s:String):String
    {
        var result ="Nu am gasit pe nimeni cu acest nume"
        //desfac mesajul si vad pe cine cauta
        if(s.startsWith("Caut"))
        {
            var (cuvant, nume) = s.split("*")
            if(persons.size == 0)
            {
                return "Inca nu este nimeni inregistrat"
            }
            for(c in 0 until persons.size)
            {
                if (persons[c]!!.nume == nume)
                {
                    result = "gasit localhost: " + persons[c]!!.adress+
                            " port: "+ persons[c]!!.port.toString()
                }
            }

        }
        return result

    }

   private fun deconectare(port:Int)
   {
       if(persons.isEmpty())
       {
           return
       }
       for(c in 0 until persons.size)
       {
           if(persons[c]!!.port==port)
           {
               persons.remove(c)
               return
           }

       }

   }
    private fun sendMessage(socket: Socket, mess:String)
    {
        socket.getOutputStream().write((mess+"\n").toByteArray())
    }

    fun run()
    {
        //creez serverul
        server = ServerSocket(SERVER_PORT)
        println("Serverul se executa pe portul: ${server.localPort}")
        println("Astept sa dirijez")
        while(true)
        {
            //astept sa se conecteze clientii
            val clientConnected = server.accept()
            thread {
                synchronized(persons)
                {
                    //partea de inregistrare in lista
                    indice = indice + 1
                    persons[indice] = Person(lista[indice],clientConnected.port,
                        clientConnected.localAddress)
                }
                //creez partea de comunicare, de unde pot citi mesajele
                val bufferReader =
                    BufferedReader(InputStreamReader(clientConnected.inputStream))
                //primirea de mesaje
                while(true)
                {
                    val mess = bufferReader.readLine()
                    println("Am primit mesajul " + mess)
                    if(mess==null)
                    {
                        deconectare(clientConnected.localPort)
                    }
                    else
                    {
                        var result = handleMessage(mess)
                       sendMessage(clientConnected, result)
                    }

                }
            }


        }

    }

}

fun main(args: Array<String>) {
    val dirijorMicrosevice =DirijorMicrosevice()
    dirijorMicrosevice.run()
}