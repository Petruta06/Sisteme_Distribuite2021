package com.sd.laborator;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import javax.inject.*;
import java.util.function.Function;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@FunctionBean("s1")
class S1Function : FunctionInitializer(), Function<S1Request, S1Reponse> {
    @Inject
    private lateinit var s1Service: S1SieveService
    private val LOG: Logger =
        LoggerFactory.getLogger(S1Function::class.java)
    override fun apply(msg : S1Request) : S1Reponse {
        var response = S1Reponse()
        LOG.info("Calculez")
        var A = s1Service.generate(100)
        response.setElements(s1Service.calculeaza(A))
        response.setMessage("Am terminat de calculat!")
        LOG.info("Calcul incheiat!")
        return response

    }   
}

/**
 * This main method allows running the function as a CLI application using: echo '{}' | java -jar function.jar 
 * where the argument to echo is the JSON to be parsed.
 */
fun main(args : Array<String>) { 
    val function = S1Function()
    function.run(args,
        { context ->
            function.apply(context.get(S1Request::class.java))})
}