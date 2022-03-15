package org.examen.components

import org.examen.interfaces.CartesianProductOperation
import org.examen.interfaces.PrimeNumberGenerator
import org.examen.interfaces.UnionOperation
import org.examen.model.Stack
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StackAppComponent {
    private var A : Stack? = null
    private var B : Stack? = null

    @Autowired
    private lateinit var primeNumberGenerator: PrimeNumberGenerator
    @Autowired
    private lateinit var cartesianProductOperation: CartesianProductOperation
    @Autowired
    private lateinit var unionOperation: UnionOperation
    @Autowired
    private lateinit var connectionFactory : RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate(){
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${stackapp.rabbitmq.queue}"])
    fun receiveMessage(msg : String){
        val processed_msg = (msg.split(",").map{it.toInt().toChar()}).joinToString(separator = "")
        var result : String ? = when(processed_msg){
                "compute" -> computeExpression()
                "regenerate_A" -> regenerateA()
                "regenerate_B" -> regenerateB()
                else -> null
        }
        println("result : \n${result}")
        if(result != null){
            sendMessage(result)
        }
    }

    fun sendMessage(msg : String){
        println("Message : \n${msg}")
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange(), connectionFactory.getRoutingKey(), msg)
    }

    private fun generateStack(count : Int) : Stack?{
        if(count < 1)
            return null
        var X : MutableSet<Int> = mutableSetOf()
        while(X.count() < count)
            X.add(primeNumberGenerator.generatePrimeNumber())
        return Stack(X)
    }

    private fun computeExpression() : String{
        if(A == null)
            A = generateStack(20)
        if(B == null)
            B = generateStack(20)
        if(A!!.data.count() == B!!.data.count()){
            // (A x B) U (B x B)
            val partialResult1 = cartesianProductOperation.executeOperation(A!!.data, B!!.data)
            val partialResult2 = cartesianProductOperation.executeOperation(B!!.data, B!!.data)
            val result = unionOperation.executeOperation(partialResult1, partialResult2)
            return "compute~" + "{\"A\": \"" + A?.data.toString() + "\", \"B\": \"" + B?.data.toString() + "\", \"result\": \"" + result.toString() + "\"}"
        }
        return "compute~" + "Error: A.count() != B.count()"
    }

    private fun regenerateA(): String {
        A = generateStack(20)
        return "A~" + A?.data.toString()
    }
    private fun regenerateB(): String {
        B = generateStack(20)
        return "B~" + B?.data.toString()
    }
}