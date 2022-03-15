package org.example

import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.function.MapFunction
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import java.io.Serializable

class Line : Serializable{
    var chr  : String? = null
}

fun main(args : Array<String>){
    val sparkSession = SparkSession.builder()
        .appName("Histogram")
        .config("spark.master", "local")
        .orCreate

    var histogram : MutableMap<Char, Int> = mutableMapOf()

    val linesRDD : JavaRDD<Line> = sparkSession
        .read()
        .textFile("src/main/resources/book.epub")
        .javaRDD()
        .map{ line ->
            line.split(" ").flatMap { it.toList()}
        }
        .flatMap{it.iterator()}
        .map{line ->
            var candidate = line.toString()
            var instance = Line()
            instance.chr = candidate
            instance
        }

    val charactersDF : Dataset<Row> = sparkSession.createDataFrame(linesRDD, Line::class.java)
    charactersDF.createOrReplaceTempView("characters")
    val characters = sparkSession.sql("SELECT * FROM characters")
    characters.groupBy("chr").count().show()
}