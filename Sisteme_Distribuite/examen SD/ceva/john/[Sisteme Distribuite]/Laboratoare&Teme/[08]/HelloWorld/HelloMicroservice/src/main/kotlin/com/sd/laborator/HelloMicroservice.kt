package com.sd.laborator

import java.net.ServerSocket

fun main(args: Array<String>) {
    val server = ServerSocket(2000)
    println("Microserviciul se executa pe portul : ${server.localPort}")
    println("Se asteapta conexiuni...")

    while(true){
        // se asteapta conexiuni din partea clientilor
        val client = server.accept()
        println("Clientul conectat : ${client.inetAddress.hostAddress} : ${client.port}")

        // acest microserviciu simplu raspunde printr-un mesaj oricarui client se conecteaza
        client.getOutputStream().write("Hello from dockerized microservice!\n".toByteArray())

        // dupa ce mesajul este trimis, se inchide conexiunea cu clientul
        client.close()
    }
}

