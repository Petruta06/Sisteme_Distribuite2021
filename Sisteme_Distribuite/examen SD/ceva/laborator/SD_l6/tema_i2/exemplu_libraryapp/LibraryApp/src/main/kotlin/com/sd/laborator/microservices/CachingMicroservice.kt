package com.sd.laborator.microservices


import com.sd.laborator.controllers.RabbitMqController

import com.sd.laborator.interfaces.CachingDAO
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.controller
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class CachingMicroservice  {
    @Autowired
    private lateinit var cachingDAO: CachingDAO

    @Autowired
    private lateinit var  rabbitMqController: RabbitMqController

    @Autowired
    private lateinit var amqpTemplate: AmqpTemplate

    @RabbitListener(queues = ["\${librarie.rabbitmq.queue1}"])

    fun fetch(message: String) {
        /*apelez fct de verificare din a elementelor*/
        var proccesMessage = message.split('\n')
        var result: String? = ""
        if(message.contains("init"))
        {
            cachingDAO.createCacheTable()
            return
        }
        if (message.contains("search ")) {
            //fac o cautare in BD
            result = cachingDAO.exist(proccesMessage[1])
        }
        else
        {
            if(message.contains("add "))
            {
                //adaug in BD
                result = cachingDAO.addToCache(proccesMessage[1], proccesMessage[2])
            }

        }


        sendMessage(result)
    }

    fun sendMessage(msg:String?)
    {
        println("Mesajul de trimis este: ")
        println(msg)
        this.amqpTemplate.convertAndSend(rabbitMqController.getExchange(),
            rabbitMqController.getRoutingKey(), msg)
    }

}