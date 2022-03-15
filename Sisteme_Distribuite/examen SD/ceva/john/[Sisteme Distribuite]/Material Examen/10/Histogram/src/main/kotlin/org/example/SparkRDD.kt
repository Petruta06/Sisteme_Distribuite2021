package org.example

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.Function
import org.apache.spark.api.java.function.Function2
import org.apache.spark.storage.StorageLevel

/*
internal class GetCharactes : Function<String?, List<Char>?>{
    override fun call(epubLine : String?) : List<Char>?{
        return epubLine!!.split(" ").flatMap { it.toList() }
    }
}

fun main(args: Array<String>) {
    // configurarea Spark
    val sparkConf = SparkConf()
        .setMaster("local")
        .setAppName("Spark Example")

    // initializarea contextului Spark
    val sparkContext = JavaSparkContext(sparkConf)


    val epubLines = sparkContext.textFile("src/main/resources/book.epub")
    epubLines.persist(StorageLevel.MEMORY_ONLY())

    var histogram : MutableMap<Char, Int> = mutableMapOf()

    epubLines.map(GetCharactes()).flatMap{it?.iterator()}.toLocalIterator().forEach{
        if(histogram.containsKey(it)){
            var aux = histogram[it]!! + 1
            histogram[it] = aux
        }
        else{
            histogram[it] = 1
        }
    }
    print(histogram)
    sparkContext.stop()
}
*/