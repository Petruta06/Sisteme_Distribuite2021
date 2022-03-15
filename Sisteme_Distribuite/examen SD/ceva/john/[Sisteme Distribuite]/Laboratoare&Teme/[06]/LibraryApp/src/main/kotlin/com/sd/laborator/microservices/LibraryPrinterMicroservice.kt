package com.sd.laborator.microservices

import com.sd.laborator.controller.RabbitMqController
import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.interfaces.LibraryPrinter
import com.sd.laborator.model.Book
import com.sd.laborator.services.LibraryDAOService
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Controller
class LibraryPrinterMicroservice {
    @Autowired
    private lateinit var libraryDAO: LibraryDAO

    @Autowired
    private lateinit var libraryPrinter: LibraryPrinter

    @Autowired
    private lateinit var rabbitMqController: RabbitMqController

    @Autowired
    private lateinit var amqpTemplate: AmqpTemplate

    var messageFromQueue : String = ""

    @RabbitListener(queues = ["\${libapp.rabbitmq.queue}"])
    fun fetchMessage(message : String){
        val processed_msg = message
        var result : String? = ""
        if(processed_msg.contains("exists ["))
            result = this.cacheHit(processed_msg.substring("exists [".length + 1, processed_msg.length - 1))
        else if(processed_msg.contains("not found ["))
            result = this.cacheMiss(processed_msg.substring("not found [".length + 1, processed_msg.length - 1))
        println("result : " + result)
        messageFromQueue = result!!
    }

    fun sendMessage(message: String){
        println("Message : " + message)
        this.amqpTemplate.convertAndSend(rabbitMqController.getExchange(), rabbitMqController.getRoutingKey(), message)
    }

    fun cacheHit(result : String) : String{
        val (query, time, response) = result.split(",")
        val objectTime = LocalTime.parse(time,DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        val objectTimeMinutes = objectTime.hour * 60 + objectTime.minute
        val currentTimeMinutes = LocalTime.now().hour * 60 + LocalTime.now().minute
        if(currentTimeMinutes - objectTimeMinutes < 60 )
            return response
        else
            return cacheMiss(query)
    }

    fun cacheMiss(result : String) : String{
        var value : String = ""
        var invalid : Boolean = false
        if(result.contains("/print?format=")){
            if(result.contains("html"))
                value = libraryPrinter.printHTML(libraryDAO.getBooks())
            if(result.contains("json"))
                value = libraryPrinter.printJSON(libraryDAO.getBooks())
            if(result.contains("raw"))
                value = libraryPrinter.printRaw(libraryDAO.getBooks())
            else
                invalid = true
        }
        if(result.contains("/init")){
            libraryDAO.cleanBooksTable()
            libraryDAO.createBooksTable()
            value =  "Database successfully initialized!"
        }
        if(result.contains("/find?")){
            val field = result.substring(result.indexOf("=") + 1)
            if(result.contains("author"))
                value = this.libraryPrinter.printJSON(this.libraryDAO.findAllByAuthor(field))
            if(result.contains("title"))
                value = this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(field))
            if(result.contains("publisher"))
                value = this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(field))
        }
        if(!invalid) {
            val currentTime = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()
            sendMessage("add [${result},${currentTime},value]")
            return value
        }
        return "Not implemented"
    }
    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String): String {
        sendMessage("search [/print?format=${format}]")
        return messageFromQueue
    }

    @RequestMapping("/init", method = [RequestMethod.GET])
    @ResponseBody
    fun initializeDataBase() : String{
        sendMessage("search [/init]")
        return messageFromQueue
    }

    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(@RequestParam(required = false, name = "author", defaultValue = "") author: String,
                   @RequestParam(required = false, name = "title", defaultValue = "") title: String,
                   @RequestParam(required = true, name = "publisher", defaultValue = "") publisher: String): String {
        var query = "/find";
        var queryValues: String = ""
        if (author != "") {
            if (queryValues.isEmpty())
                queryValues += "?"
            else
                queryValues += "&"
            queryValues += "author=${author}"
        }
        if (title != "") {
            if(queryValues.isEmpty())
                queryValues += "?"
            else
                queryValues += "&"
            queryValues += "title=${title}"

        }
        if (publisher != "") {
            if(queryValues.isEmpty())
                queryValues += "?"
            else
                queryValues += "&"
            queryValues += "publisher=${publisher}"
        }
        query += queryValues;
        sendMessage("search [${query}]")
        return messageFromQueue
    }

    @RequestMapping("/find/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customFindAndPrint(@RequestParam(required = false, name = "author", defaultValue = "") author: String,
                           @RequestParam(required = false, name = "title", defaultValue = "") title: String,
                           @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String,
                           @RequestParam(required = false, name = "format", defaultValue = "") format : String) : String{
        var criterium : String = ""
        var entry : String = ""
        if (author != "")
            criterium = "author"
            entry = author
        if (title != "")
            criterium = "title"
            entry = title
        if (publisher != "")
            criterium = "publisher"
            entry = publisher
        return when(format) {
            "html" -> libraryPrinter.printHTML(getCustomEntries(criterium,entry))
            "json" -> libraryPrinter.printJSON(getCustomEntries(criterium,entry))
            "raw" -> libraryPrinter.printRaw(getCustomEntries(criterium,entry))
            else -> "Not implemented"
        }
    }

    fun getCustomEntries(criterium : String, entry : String) : Set<Book?>{
        if(criterium.equals("author")){
            return this.libraryDAO.findAllByAuthor(entry)
        }
        if(criterium.equals("title")){
            return this.libraryDAO.findAllByTitle(entry)
        }
        if(criterium.equals("publisher")){
            return this.libraryDAO.findAllByPublisher(entry)
        }
        return this.libraryDAO.getBooks()
    }
}
