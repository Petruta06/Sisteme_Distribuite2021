package com.sd.laborator.microservices


import com.sd.laborator.controller.RabbitMqController
import com.sd.laborator.interfaces.CachingDAO
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CachingMicroservice {
    @Autowired
    private lateinit var cachingDAO: CachingDAO

    @Autowired
    private lateinit var  rabbitMqController: RabbitMqController

    @Autowired
    private lateinit var amqpTemplate: AmqpTemplate

    @RabbitListener(queues = ["\${caching_client.rabbitmq.queue}"])
    fun fetch(message: String)
    {
        /*apelez fct de verificare din a elementelor*/
        var sir: String? = cachingDAO.exist(message)
        if( sir.equals("0"))
        {
            sendMessage("Fa-o interogare!")
        }
        if( sir.equals("1"))
        {
            sendMessage("Inregistreaza in tabel!")
        }


    }

    fun sendMessage(msg:String)
    {
        println("Mesajul de trimis este: ")
        println(msg)
        this.amqpTemplate.convertAndSend(rabbitMqController.getExchange(),
        rabbitMqController.getRoutingKey(), msg)
    }






}