package org.examen;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.*;
import java.util.function.Function

@FunctionBean("producer")
class ProducerFunction : FunctionInitializer(), Function<Producer, Consumer> {

    override fun apply(msg : Producer) : Consumer {
        val url : String = msg.getUrl()
        var content : StringBuilder = StringBuilder()

        with(URL(url).openConnection() as HttpURLConnection){
            requestMethod = "GET"
            inputStream.bufferedReader().use{
                it.lines().forEach{
                    line -> content.append(line)
                }
            }
        }

        val consumer : Consumer = Consumer()
        consumer.setContent(content.toString())
        return consumer
    }   
}

fun main(args : Array<String>) { 
    val function = ProducerFunction()
    function.run(args, { context -> function.apply(context.get(Producer::class.java))})
}