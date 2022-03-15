package org.examen.components

import org.examen.interfaces.CalculatorInterface
import org.examen.interfaces.ParserInterface
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CalculatorComponent{
    @Autowired
    private lateinit var expressionParser : ParserInterface

    @Autowired
    private lateinit var calculatorInterface: CalculatorInterface

    @Autowired
    private lateinit var connectionFactoryComponent: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate(){
        this.amqpTemplate = connectionFactoryComponent.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${app.rabbitmq.queue}"])
    fun recieveMessage(message : String){
        val parsedMessage = expressionParser.parseExpression(message)
        if(parsedMessage == null){
            sendMessage("Expresie invalida! Te rog sa nu incluzi caractere alfabetice!")
            return
        }
        val expressionResult = calculatorInterface.computeExpression(parsedMessage)
        sendMessage(expressionResult)
    }

    fun sendMessage(msg : String){
        this.amqpTemplate.convertAndSend(connectionFactoryComponent.getExchange(),
                                        connectionFactoryComponent.getRoutingKey(),
                                        msg)
    }
}