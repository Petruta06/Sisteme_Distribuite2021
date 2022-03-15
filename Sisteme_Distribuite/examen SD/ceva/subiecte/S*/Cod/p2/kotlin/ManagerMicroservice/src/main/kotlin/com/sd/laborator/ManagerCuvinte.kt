package com.sd.laborator

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import kotlin.concurrent.thread

class ManagerCuvinte {
    private val manager:ServerSocket
    private var dict:HashMap<String, Int> = hashMapOf()

    companion object
    {
        const val MANAGER_PORT = 1234
    }
    init{
        manager = ServerSocket(MANAGER_PORT)
    }
    //functie pentru citirea cuvintelor din fisier
// si construierea map
    private fun readWord()
    {

        var f = File("/home/ana/Desktop/examen SD/subiecte/S*/Cod/p2/kotlin/ManagerMicroservice/cuvinte.txt")
        if(f.exists())
        {
            var sir = f.readLines()
            for(s in sir)
            {
                dict[s]= s.length
            }
            println("Am citit cuvintele din fisier")
        }
        else
        {
            println("fisierul nu exista!")
        }

    }
    fun run()
    {

        println("Sunt in fct run a managerului")
        readWord()
       try {

               println("Astept sa se conecteze clienti")
               val client :Socket = manager.accept()
               println("S-a conectat ${manager.localPort}")
               handle(client)


       }
       catch (se:SocketException)
       {
           println(se)
       }
    }
    private  fun handle(s:Socket)
    {
        try {
            println("Prelucrez datele de pe socket")
            val buff = BufferedReader(InputStreamReader(s.getInputStream()))

            while(true)
            {
                println("Am intrat in while")
                var sir = buff.readLine()
                println(sir)
                if(sir!=null)
                {
                    thread {
                        var result = "*" + prelucrare(sir)
                        println("Mesajul de trimis este\n" +result)
                        s.getOutputStream().write(result.toByteArray())
                    }
                }


            }

        }
        catch (ex:Exception)
        {
            println(ex)
        }

    }
    private fun prelucrare(s:String):String{
        var word = s.split(" ")
        var result =""
        println(word)
        println("fct de prelucrare")
        for(c in word)
        {
            if(c in dict)
            {
                result +=dict[c].toString()+" "
            }
            else {
                result += c + " "
            }


        }
        return result
    }



}
fun main(args: Array<String>) {
    val managerCuvinte = ManagerCuvinte()
    managerCuvinte.run()
}