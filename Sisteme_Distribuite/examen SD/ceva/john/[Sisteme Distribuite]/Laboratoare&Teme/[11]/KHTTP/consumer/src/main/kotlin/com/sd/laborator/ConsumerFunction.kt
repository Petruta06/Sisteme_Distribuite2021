package com.sd.laborator;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.util.function.Function;
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

@FunctionBean("consumer")
class ConsumerFunction : FunctionInitializer(), Function<XMLContent, Consumer> {

    override fun apply(xmlContent: XMLContent) : Consumer {
        val consumer : Consumer = Consumer()
        val xmlDoc = this.readXML(xmlContent.GetContent())
        val titles = this.GetElementValue(xmlDoc,"title")
        val urls = this.GetElementValue(xmlDoc, "href")
        for(index in 0..titles.size){
            consumer.AddPair(Pair("title",titles[index]), Pair("url",urls[index]))
        }
        return consumer.GetContent()
    }

    private fun readXML(content : String) : Document {
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dbBuilder = dbFactory.newDocumentBuilder()
        return dbBuilder.parse(InputSource(content))
    }

    private fun GetElementValue(doc : Document, elementName : String) : List<String>{
        val xpFactory = XPathFactory.newInstance()
        val xPath = xpFactory.newXPath()

        val xpath = "/ItemSet/$elementName"
        val elementNodeList = xPath.evaluate(xpath, doc, XPathConstants.NODESET) as NodeList
        var elementsList = mutableListOf<String>()
        for(index in 0..elementNodeList.length){
            elementsList.add(index,elementNodeList.item(index).nodeValue)
        }
        return elementsList
    }
}

fun main(args : Array<String>) { 
    val function = ConsumerFunction()
    function.run(args, { context -> function.apply(context.get(XMLContent::class.java))})
}