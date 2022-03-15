package com.sd.laborator.microservices

import com.sd.laborator.controller.RabbitMqController
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

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = rabbitMqController.rabbitTemplate()
    }

     /*@RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String): String {
        return when(format) {
            "html" -> libraryPrinter.printHTML(libraryDAO.getBooks())
            "json" -> libraryPrinter.printJSON(libraryDAO.getBooks())
            "raw" -> libraryPrinter.printRaw(libraryDAO.getBooks())
            else -> "Not implemented"
        }
    }

   @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(@RequestParam(required = false, name = "author", defaultValue = "") author: String,
                   @RequestParam(required = false, name = "title", defaultValue = "") title: String,
                   @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String): String {
        if (author != "")
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByAuthor(author))
        if (title != "")
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(title))
        if (publisher != "")
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(publisher))
        return "Not a valid field"
    }*/

    @RabbitListener(queues = ["\${librarie.rabbitmq.queue}"])
    fun fetch(message: String) {

        val process = (message.split(",").map { it.toInt().toChar() }).joinToString(separator = "")
        var result: Any? = null
        /*if (process == "start") {
            libraryDAO.createTable()
        } else {*/
            if (process == "all") {
                libraryDAO.getBooks()

            } else {
                if ("author=" in process && "text=" in process && "name=" in process && "publisher=" in process) {
                    result = libraryDAO.addBook(
                        Book(
                            Content(
                                100,
                                process.get(1).toString(),
                                process[3].toString(),
                                process[5].toString(),
                                process[7].toString()
                            )
                        )
                    )

                } else {
                    if ("author=" in process) {
                        result = libraryDAO.findAllByAuthor(process[1].toString())
                    } else {
                        if ("name=" in process) {
                            result = libraryDAO.findAllByTitle(process[1].toString())
                        } else {
                            if ("publisher=" in process) {
                                result = libraryDAO.findAllByPublisher(process[1].toString())
                            }
                        }
                    }
                }
            }


            println("result:")
            println(result)
            if (result != null) {
                sendMessage(result.toString())
            }

        }




    fun sendMessage(msg:String)
    {
        println("Mesajul de trimis este: ")
        println(msg)
        this.amqpTemplate.convertAndSend(rabbitMqController.getExchange(), rabbitMqController.getRoutingKey(), msg)
    }


}