package com.sd.laborator

import org.apache.spark.SparkConf
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.api.java.JavaStreamingContext


fun prelucrare(date:List<String>)
{
    if(date.size!=0)
    {
        println("Prelucrez date!")
        date.forEach{
            var s = it.split("{")
            s.forEach{
                var i = it.split("\n")
                println(i[1] +"\t" + i[2])
                var c = i[2].split(":")
                var max = c[1].toInt()
                if(max>400)
                {
                    println("Compania cu " + i[1] + "indeplineste conditia profit > 400")
                }
            }
        }
        println("Am terminat de prelucrat datele!")
    }
}

fun main(args: Array<String>)
{
    println("Creez configurez Spark")
    val sparkConf = SparkConf().setMaster("local").setAppName("Examen")
    val sparkContext = JavaStreamingContext(sparkConf, Durations.seconds(1))

    val streamSocket = sparkContext.socketTextStream("localhost", 1234)
    println("Astept sa primesc date")
    streamSocket.foreachRDD{ rdd->prelucrare(rdd.collect()) }

    sparkContext.start()
    sparkContext.awaitTermination()

}