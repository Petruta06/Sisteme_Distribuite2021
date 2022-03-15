package com.sd.laborator;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import javax.inject.*;
import java.util.function.Function;

@FunctionBean("hello-world-maven")
class HelloWorldMavenFunction : FunctionInitializer(), Function<HelloWorldMaven, HelloWorldMaven> {
    override fun apply(msg : HelloWorldMaven) : HelloWorldMaven {
         return msg
    }   
}
fun main(args : Array<String>) { 
    val function = HelloWorldMavenFunction()
    function.run(args, { context ->
        function.apply(context.get(HelloWorldMaven::class.java))
    })
}