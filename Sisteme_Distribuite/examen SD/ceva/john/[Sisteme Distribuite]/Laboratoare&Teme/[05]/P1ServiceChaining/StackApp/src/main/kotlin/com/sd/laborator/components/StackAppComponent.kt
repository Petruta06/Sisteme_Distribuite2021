package com.sd.laborator.components

import com.sd.laborator.interfaces.CartesianProductOperation
import com.sd.laborator.interfaces.PrimeNumberGenerator
import com.sd.laborator.interfaces.SetGenerator
import com.sd.laborator.interfaces.UnionOperation
import com.sd.laborator.model.Stack
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StackAppComponent {
    private var A: Stack? = null
    private var B: Stack? = null

    @Autowired
    private lateinit var unionOperation: UnionOperation
    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent
    @Autowired
    private lateinit var setGenerator: SetGenerator

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${stackapp.rabbitmq.queue}"])
    fun recieveMessage(msg: String) {
        // the result: 114,101,103,101,110,101,114,97,116,101,95,65 --> needs processing
        val processed_msg = (msg.split(",").map { it.toInt().toChar() }).joinToString(separator="")
        var result: String? = when(processed_msg) {
            "compute" -> computeExpression()
            "regenerate_A" -> regenerateSet("A")
            "regenerate_B" -> regenerateSet("B")
            else -> null
        }
        println("result: ")
        println(result)
        if (result != null) sendMessage(result)
    }

    fun sendMessage(msg: String) {
        println("message: ")
        println(msg)
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange(),
            connectionFactory.getRoutingKey(),
            msg)
    }

    private fun generateStack(count: Int): Stack? {
        return setGenerator.setGenerate(count)
    }

    private fun computeExpression(): String {
        if (A == null)
            A = generateStack(20)
        if (B == null)
            B = generateStack(20)
        if (A!!.data.count() == B!!.data.count()) {
            val result = unionOperation.executeOperation(A!!, B!!)
            return "compute~" + "{\"A\": \"" + A?.data.toString() +"\", \"B\": \"" + B?.data.toString() + "\", \"result\": \"" + result.toString() + "\"}"
        }
        return "compute~" + "Error: A.count() != B.count()"
    }

    private fun regenerateSet(type : String): String {
        if(type.toLowerCase().equals("a")){
            A = setGenerator.setGenerate(20)
            return "A~" + A?.data.toString()
        }
        else if(type.toLowerCase().equals("b")){
            B = setGenerator.setGenerate(20)
            return "B~" + B?.data.toString()
        }
        return null!!
    }

}
