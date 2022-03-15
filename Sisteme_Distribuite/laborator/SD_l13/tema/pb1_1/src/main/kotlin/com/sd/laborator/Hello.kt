package com.sd.laborator

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.Function


fun String.prelucrare():String
{
    var result:String = ""
    var i = 0
    for(c in this)
    //for i in range(
    {
        if(i%2==0)
        {
            result += c.toLowerCase()
        }
        else{
            result += c.toUpperCase()
        }
        i = i + 1
    }
    return result
}

fun main(args: Array<String>) {

    val sparkConf = SparkConf().setMaster("local").setAppName("Tema 1")

    val sparkContext = JavaSparkContext(sparkConf)

    //lista mea de itemi
    val items = listOf("1m34k!", "ana", "mare", "vacanta")

    val colectii = sparkContext.parallelize(items)

    //filtarea listei si retinerea doar a literelor
    val litere = colectii.filter{
        it.matches(Regex("[a-zA-Z]+"))
    }

    println(litere)

    val colectieNoua = litere.map(
        object:Function<String?, String?>{
            override fun call(p0: String?): String? {
                if(p0!=null)
                {
                    return p0.prelucrare()
                }
                else
                {
                    return p0
                }

            }
        }
    )




}

