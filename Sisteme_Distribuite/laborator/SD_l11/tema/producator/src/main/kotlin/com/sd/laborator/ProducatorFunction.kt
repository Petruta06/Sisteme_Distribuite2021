package com.sd.laborator;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import javax.inject.*;
import java.util.function.Function;
import java.util.function.Supplier
import khttp.get

@FunctionBean("producator")
class ProducatorFunction : FunctionInitializer(), Supplier<Producator> {

    override fun get(): Producator {
        var p = Producator()



        TODO("Not yet implemented")
    }

}


/**
 * This main method allows running the function as a CLI application using: echo '{}' | java -jar function.jar 
 * where the argument to echo is the JSON to be parsed.
 */
fun main(args : Array<String>) { 
    val function = ProducatorFunction()
    function.run(args, { context -> function.get()})
}