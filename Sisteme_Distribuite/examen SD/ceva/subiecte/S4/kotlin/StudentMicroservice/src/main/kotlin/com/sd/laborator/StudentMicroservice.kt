package com.sd.laborator

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.system.exitProcess
import org.apache.spark.streaming.api.java.JavaStreamingContext

class StudentMicroservice {
    private lateinit var questionDatabase:MutableList<Pair<String, String>>
    private lateinit var pythonSocket:Socket
    val sparkConf = SparkConf().setMaster("local").setAppName("Spark Example")
    //val sparkContext = JavaSparkContext(sparkConf)

        companion object Constants {

            val PYTHON_HOST = "localhost"
            const val PYTHON_PORT = 1600
        }


    private fun subscribeToMessageManager()
    {
        try {
            pythonSocket = Socket(PYTHON_HOST, PYTHON_PORT)
            println("M-am conectat la serverul Python!")
        }
        catch(e:Exception)
        {
            println("Nu ma pot conecta  serverul Python")
            exitProcess(1)
        }

    }
private fun prelucrare(s:List<String>):List<String> {
    return s.filter { it ->
        it.length > 10
    }
}



   fun run() {
       //subscribeToMessageManager()

       //println("StudentMicroservice se executa pe portul:${pythonSocket.localPort}")
       //println("Se asteapta mesaje...")
       /*val bufferReader =
           BufferedReader(InputStreamReader(pythonSocket.inputStream))

       while (true) {
           val response = bufferReader.readLine()

           if (response == null) {
               println(
                   "Microserviciul MessageService " +
                           "${pythonSocket.port} a fost oprit."
               )
               bufferReader.close()
               pythonSocket.close()
               break
           }
           thread {
               println(response)


           }*/
       val streamContext = JavaStreamingContext(sparkConf, Durations.seconds(1) )

       val stream: JavaReceiverInputDStream<String> = streamContext.socketTextStream(PYTHON_HOST, PYTHON_PORT)

       stream.foreachRDD{
           date ->
           date.filter{
               it ->it.length > 10
           }
       }
       streamContext.start()
       streamContext.awaitTerminationOrTimeout(1000)

   }
    }

fun main(args: Array<String>) {
    val studentMicroservice = StudentMicroservice()
    studentMicroservice.run()
}
