package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer


@EnableBinding(Processor::class)
@SpringBootApplication
class PipeProccesor {
    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    fun transform( sir:String): Any? {
        var result = "Am prelucrat "

        while(sir.equals("gata!"))
        {
            result = result +"\n" + sir
        }
        return result
    }
}
fun main(args: Array<String>) {
    runApplication<PipeProccesor>(*args)
}