package com.sd.laborator.microservices

import com.sd.laborator.interfaces.CachingDAO
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class CachingMicroservice {
    @Autowired
    private lateinit var cachingDAO: CachingDAO

    @Autowired
    private lateinit var rabbitMqController : RabbitMq

    @Autowired
    private lateinit var amqpTemplate : AmqpTemplate

    @RabbitListener(queues = ["\${libapp.rabbitmq.queue1}"])
    fun fetchMessage(message : String){
        val processed_msg = message
        var result : String? = ""
        if(processed_msg.contains("search [")) {
            val query = processed_msg.substring("search [".length + 1, processed_msg.length - 1)
            result = cachingDAO.exists(query)
            sendMessage(result!!)
        }
        else if(processed_msg.contains("add [")) {
            val intermediate = processed_msg.substring("add [".length + 1, processed_msg.length - 1)
            val (query, time, resulty)  = intermediate.split(",")
            result = cachingDAO.addToCache(query,time, resulty)
        }
    }

    fun sendMessage(message: String){
        println("Message : " + message)
        this.amqpTemplate.convertAndSend(rabbitMqController.getExchange(), rabbitMqController.getRoutingKey(), message)
    }

}