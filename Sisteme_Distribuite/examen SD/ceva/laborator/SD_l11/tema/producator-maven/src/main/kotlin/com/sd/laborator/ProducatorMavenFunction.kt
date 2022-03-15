package com.sd.laborator;


import io.micronaut.function.FunctionBean
import io.micronaut.function.executor.FunctionInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Supplier
import java.util.function.Function
import javax.inject.Inject


import khttp.get
import khttp.responses.Response
import java.io.File

@FunctionBean("producator-maven")
class ProducatorMavenFunction : FunctionInitializer(), Supplier<ProducatorMaven> {

    private val LOG: Logger =LoggerFactory.getLogger(ProducatorMavenFunction::class.java)
    override fun get(): ProducatorMaven {
        var p = ProducatorMaven()
        val r:Response = get("https://xkcd.com/atom.xml")
        //var jsonObj = r.jsonArray
        //p.continut = jsonObj.toString()

        LOG.info("p.continut")
        LOG.error("eroare")
       // File("application.log").writeText(r.content.toString())
        return p
    }

}

/**
 * This main method allows running the function as a CLI application using: echo '{}' | java -jar function.jar 
 * where the argument to echo is the JSON to be parsed.
 */
fun main(args : Array<String>) { 
    val function = ProducatorMavenFunction()
    function.run(args, { context -> function.get()})
}