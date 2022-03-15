package com.sd.laborator.components

import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.interfaces.LibraryPrinter
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class LibraryAppComponent {
    @Autowired
    private lateinit var libraryDAO: LibraryDAO

    @Autowired
    private lateinit var libraryPrinter: LibraryPrinter

    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent
    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    fun sendMessage(msg: String) {
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange(),
                                         connectionFactory.getRoutingKey(),
                                         msg)
    }

    @RabbitListener(queues = ["\${libraryapp.rabbitmq.queue}"])
    fun recieveMessage(msg: String) {
        // the result needs processing
        val processedMsg = (msg.split(",").map { it.toInt().toChar() }).joinToString(separator="")
        try {
            if(!processedMsg.toLowerCase().contains("insert")) {
                val message = processedMsg.split(":")
                val function = message[0]
                val format = message[1]
                val parameter = if (function == "find") {
                    message[2]
                } else {
                    null
                }
                //val (function, parameter, extra) = processedMsg.split(":")
                val result: String? = when (function) {
                    "print" -> customPrint(format)
                    "find" -> customFind(parameter!!, format)
                    else -> null
                }
                if (result != null) sendMessage(result)
            }
            else{
                val message = processedMsg.substring(processedMsg.indexOf('[') + 1, processedMsg.length - 1)
                val (title, author, publisher, content) = message.split(":")
                this.addBook(Book(Content(author,content,title,publisher)))
                sendMessage("Adaugare realizata cu succes!")
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    fun customPrint(format: String): String {
        return libraryPrinter.print(libraryDAO.getBooks(),format)
    }

    fun customFind(searchParameter: String, format: String): String {
        val (field, value) = searchParameter.split("=")
        return when(field) {
            "author" -> this.libraryPrinter.print(this.libraryDAO.findAllByAuthor(value),format)
            "title" -> this.libraryPrinter.print(this.libraryDAO.findAllByTitle(value),format)
            "publisher" -> this.libraryPrinter.print(this.libraryDAO.findAllByPublisher(value),format)
            else -> "Not a valid field"
        }
    }

    fun addBook(book: Book): Boolean {
        return try {
            this.libraryDAO.addBook(book)
            true
        } catch (e: Exception) {
            false
        }
    }

}