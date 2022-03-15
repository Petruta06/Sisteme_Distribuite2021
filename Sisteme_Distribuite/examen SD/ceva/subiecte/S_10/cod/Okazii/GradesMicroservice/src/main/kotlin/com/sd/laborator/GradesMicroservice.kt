package com.sd.laborator

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketTimeoutException

class GradesMicroservice
{
    private var serverGrades :ServerSocket
    private lateinit var socketBidders:Socket
    private lateinit var obs: Observable<String>
    private val bidderConnections:MutableList<Socket> = mutableListOf()

    companion object Constants
    {
        const val GRADES_PORT = 1900
        const val BIDDER_LOCALHOST = "localhost"
        const val BIDDER_PORT = 1234
    }

    init {
        serverGrades = ServerSocket(GRADES_PORT)
        println("Serverul " +
                "pentru evaluarea aplicatiei se executa pe portul ${serverGrades.localPort}")
        println("Astept pareri!")
        obs = Observable.create<String>()
        {
            emitter ->
            while(true)
            {
                try {
                    val bidderConnection = serverGrades.accept()
                   bidderConnections.add(bidderConnection)
// se citeste mesajul de la bidder de pe socketul TCP
                    val bufferReader =
                        BufferedReader(InputStreamReader(bidderConnection.inputStream))
                    val receivedMessage = bufferReader.readLine()
// daca se primeste un mesaj gol (NULL), atunci inseamna ca cealalta parte a socket-ului a fost inchisa
                    if (receivedMessage == null) {
// deci subscriber-ul respectiv a fost deconectat
                        bufferReader.close()
                        bidderConnection.close()
                        emitter.onError(Exception("Eroare: Bidder-ul${bidderConnection.port} a fost deconectat."))
                    }
                    emitter.onNext(receivedMessage)
                }
             catch (e: SocketTimeoutException) {
            emitter.onComplete()
            break
        }
            }
        }
    }
    private fun receiveBids()
    {
        val receiveBidsSubscription =
            obs.subscribeBy(
                onNext = {
                    val message = Message.deserialize(it.toByteArray())
                    println(message)
                },
                onComplete = {
// licitatia s-a incheiat se trimit raspunsurile mai departe catre procesorul de mesaje
                    println("Licitatia s-a incheiat! Se trimit ofertele spre procesare...")
                    //forwardBids()
                },
                onError = { println("Eroare: $it") }
            )
    }
    fun run()
    {
        receiveBids()
    }
}

fun main(args: Array<String>) {
    var gradesMicroservice = GradesMicroservice()
    gradesMicroservice.run()
}

