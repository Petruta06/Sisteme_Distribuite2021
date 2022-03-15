package com.sd.laborator;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.*;
import java.util.function.Function;
import java.util.function.Supplier

@FunctionBean("producer")
class ProducerFunction : FunctionInitializer(), Supplier<Producer> {

    override fun get() : Producer {
        val producer : Producer = Producer()

        val requestedData : StringBuilder = StringBuilder()
        val url = URL("https://xkcd.com/atom.xml")

        with(url.openConnection() as HttpURLConnection){
            requestMethod = "GET"

            // "Sent GET request to URL : $url..."
            // "Response Code : $responseCode..."

            inputStream.bufferedReader().use{
                it.lines().forEach{
                    line -> requestedData.append(line)
                }
            }
            producer.xmlContent = requestedData.toString()
        }
        return producer
    }   
}
fun main(args : Array<String>) { 
    val function = ProducerFunction()
    function.run(args, { context -> function.get() })
}