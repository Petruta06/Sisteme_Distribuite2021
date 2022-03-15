package org.example

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.api.java.JavaStreamingContext
import scala.Tuple2
import java.time.Duration

fun main(args : Array<String>){
    val sparkConf = SparkConf().setMaster("local[4]").setAppName("Network word count")

    Logger.getRootLogger().level = Level.OFF
    val streamingContext = JavaStreamingContext(sparkConf, Durations.seconds(1))
    val lines = streamingContext.socketTextStream("localhost", 9999)
//    val lines = streamingContext.textFileStream("file:///usr/resources")
    lines.foreachRDD{rdd -> println(rdd.collect())}
    var words = lines.flatMap {
        it.iterator()
    }.mapToPair{
        Tuple2(it, 1)
    }.reduceByKey{
        total, next -> total + next
    }
    words.print()
    streamingContext.start()
    streamingContext.awaitTermination()
}