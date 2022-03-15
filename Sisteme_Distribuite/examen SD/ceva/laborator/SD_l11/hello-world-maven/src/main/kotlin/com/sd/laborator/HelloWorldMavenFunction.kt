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

/**
 * This main method allows running the function as a CLI application using: echo '{}' | java -jar function.jar 
 * where the argument to echo is the JSON to be parsed.
 */
fun main(args : Array<String>) { 
    val function = HelloWorldMavenFunction()
    function.run(args, { context -> function.apply(context.get(HelloWorldMaven::class.java))})
}