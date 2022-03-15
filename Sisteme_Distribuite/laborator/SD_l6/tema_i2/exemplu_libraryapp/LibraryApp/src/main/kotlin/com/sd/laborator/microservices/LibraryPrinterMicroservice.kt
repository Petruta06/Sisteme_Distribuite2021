package com.sd.laborator.microservices

import com.sd.laborator.controllers.RabbitMqController
import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.interfaces.LibraryPrinter
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

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

    var result_queue : String = ""

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = rabbitMqController.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${librarie.rabbitmq.queue}"])
    fun fetch(message: String) {

        val process_msg = message.split("\n")
        if(message.contains("exists"))
        {
            cacheHit(message)

        }
        else
        {
            if(message.contains("nu exista"))
            {
                cacheMiss(message)
            }
        }


    }
    fun cacheHit(sir:String):String
    {
        /*sparg sirul*/
        var (neinteresant, timestamp, query, result) = sir.split('\n')
        /*calculez ora curenta */
        var currentDateTime= LocalDateTime.now()
        var ora =   (currentDateTime.format(DateTimeFormatter.ofPattern("HH"))).toInt()
        var minut =   currentDateTime.format(DateTimeFormatter.ofPattern("mm")).toInt()
        var moment_curent = ora * 60 + minut
        /*verific conditia*/
        //if((moment_curent - timestamp.toInt() < 60) and (moment_curent > timestamp.toInt()))
        if(abs(moment_curent - timestamp.toInt())<60)
        {
            /*de modificat pentru cazul in care sunt 23 si 00*/
            println("hit")
            return result
        }
        else
        {
            return cacheMiss(result)
        }

    }
    fun cacheMiss(result:String):String
    {

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

            libraryDAO.createTable()
            value =  "Database successfully initialized!"
        }

        //in fct de ce anume caut trimit interogarea
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
            var currentDateTime= LocalDateTime.now()
            var ora =   (currentDateTime.format(DateTimeFormatter.ofPattern("HH"))).toInt()
            var minut =   currentDateTime.format(DateTimeFormatter.ofPattern("mm")).toInt()
            var moment_curent = ora*60 + minut
            sendMessage("add \n ${result}\n${value}\n${moment_curent}\n ")
            return "Am trimis sa inregistreze"
        }
        return "Not implemented"

    }


    fun sendMessage(msg:String)
    {
        println("Mesajul de trimis este: ")
        println(msg)
        this.amqpTemplate.convertAndSend(rabbitMqController.getExchange(), rabbitMqController.getRoutingKey(), msg)
    }



    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String): String {

        /*return when(format) {
            "html" -> libraryPrinter.printHTML(libraryDAO.getBooks() )
            "json" -> libraryPrinter.printJSON(libraryDAO.getBooks())
            "raw" -> libraryPrinter.printRaw(libraryDAO.getBooks())
            else -> "Not implemented"
        }*/
        sendMessage("search \n /print?${format}")
        return result_queue
    }
    @RequestMapping("/init", method = [RequestMethod.GET])
    @ResponseBody
    fun initializeDataBase()  {
        sendMessage("search \n init ")

    }



    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(@RequestParam(required = false, name = "author", defaultValue = "") author: String,
                   @RequestParam(required = false, name = "title", defaultValue = "") title: String,
                   @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String): String {


        var query = "/find"
        var sir_cautat = ""
        if (author != "")
        {
            if(sir_cautat.isEmpty())
                sir_cautat += '?'
            else sir_cautat += '&'
            sir_cautat += author
        }
        if (title != "")
        {
            if(sir_cautat.isEmpty())
                sir_cautat += '?'
            else sir_cautat += '&'
            sir_cautat += title
        }

        if (publisher != "")
        {
            if(sir_cautat.isEmpty())
                sir_cautat += '?'
            else sir_cautat += '&'
            sir_cautat += publisher
        }
        sir_cautat = "search\n" + sir_cautat
        sendMessage(sir_cautat)
        return result_queue


    }

}