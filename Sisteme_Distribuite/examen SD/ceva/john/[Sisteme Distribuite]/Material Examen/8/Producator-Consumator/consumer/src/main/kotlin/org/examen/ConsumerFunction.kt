package org.examen;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import javax.inject.*;
import java.util.function.Function;

@FunctionBean("consumer")
class ConsumerFunction : FunctionInitializer(), Function<Content, Consumer> {

    override fun apply(xml : Content) : Consumer {
        val mapping : MutableList<String> = mutableListOf()

        var xmlContent : String = xml.getContent()
        var pattern = """<title>([A-z0-9\./=\"<>\s:]+)</title><link([A-z0-9\./=\"<>\s:]+)</link>""".toRegex()
        xmlContent = xmlContent.replace("\\","")
        val titles = pattern.findAll(xmlContent).map { it.groupValues[1] }.toList()
        var links = pattern.findAll(xmlContent).map { it.groupValues[2] }.toList()

        pattern = """href=\"([A-z0-9:/\..]+)\"\s""".toRegex()
        var index = 0
        links.forEach{
            var hrefs = pattern.findAll(it).map { it.groupValues[1] }.toList()
            mapping.add("(${titles[index]}, ${hrefs[0]})")
            index++;
        }
        val news : Consumer = Consumer()
        news.setNews(mapping)
        return news

    }   
}

/**
 * This main method allows running the function as a CLI application using: echo '{}' | java -jar function.jar 
 * where the argument to echo is the JSON to be parsed.
 */
fun main(args : Array<String>) { 
    val function = ConsumerFunction()
    function.run(args, { context -> function.apply(context.get(Content::class.java))})
}