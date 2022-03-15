package com.sd.laborator

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.api.java.JavaStreamingContext
import java.io.File

var mesaje = 0
val f = File("/home/ana/Desktop/examen SD/subiecte/S15/mess.txt")
fun prelucrare (list:List<String>)
{
    if(list.size!=0)
    {
        if(!f.exists())
        {
            f.createNewFile()
        }
        for(m in list)
        {
            f.appendText(m +"\n")
            mesaje = mesaje + 1
        }
    }
}

fun main(args: Array<String>)
{

    val conf = SparkConf().setMaster("local[2]").setAppName("Flux")
    val jssc = JavaStreamingContext(conf, Durations.seconds(1))

    val stream = jssc.socketTextStream("localhost", 1234)
    stream.foreachRDD { rdd-> prelucrare(rdd.collect()) }


    jssc.start()
    jssc.awaitTermination()

    f.appendText("Au fost necesare " + mesaje.toString() +" mesaje" +
            "pentru determina castigatorul!\n")


}