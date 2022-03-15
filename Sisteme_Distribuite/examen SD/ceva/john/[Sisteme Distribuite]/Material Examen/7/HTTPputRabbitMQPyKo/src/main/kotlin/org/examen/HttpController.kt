package org.examen

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HttpController {
    @Autowired
    private lateinit var rabbitMqConnectionFactoryComponent: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate(){
        amqpTemplate = rabbitMqConnectionFactoryComponent.rabbitTemplate()
    }

    @RequestMapping(value = ["/publish"], method = [RequestMethod.PUT])
    @ResponseBody
    fun publishMessage(@RequestBody message : String) : ResponseEntity<Unit>{
        println("Am trimis mesajul : ${message}!")
        this.amqpTemplate.convertAndSend(rabbitMqConnectionFactoryComponent.getExchange(), rabbitMqConnectionFactoryComponent.getRoutingKey(), message)
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }
}