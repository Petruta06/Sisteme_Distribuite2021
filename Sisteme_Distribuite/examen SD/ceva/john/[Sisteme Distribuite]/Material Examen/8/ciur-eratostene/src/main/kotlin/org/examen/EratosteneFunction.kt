package org.examen;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import io.reactivex.functions.BiFunction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.*;
import java.util.function.Function;

@FunctionBean("eratostene")
class EratosteneFunction : FunctionInitializer(), BiFunction<EratosteneNumberRequest, EratosteneListRequest, EratosteneResponse> {
    @Inject
    private lateinit var eratosteneSieveService: EratosteneSieveService

    private val LOG : Logger = LoggerFactory.getLogger(EratosteneFunction::class.java)
    override fun apply(msg : EratosteneNumberRequest, list : EratosteneListRequest) : EratosteneResponse {
        val number : Int = msg.getNumber()
        val parsedCandidates : List<Int> = list.getCandidates()

        val response = EratosteneResponse()

        if(number >= eratosteneSieveService.MAX_SIZE){
            LOG.error("Parametru prea mare!")
            response.setMessage("Se accepta doar parametri mai mici ca ${eratosteneSieveService.MAX_SIZE}")
            return response
        }
        LOG.info("Se calculeaza primele $number numere prime..")
        var primeNumbers : List<Int> = eratosteneSieveService.findPrimesLessThan(number)
        primeNumbers = primeNumbers.filter { parsedCandidates.contains(it) }
        response.setPrimes(primeNumbers)
        response.setMessage("Calcul efectuat cu succes!")
        LOG.info("Calcul incheiat!")
        return response
    }   
}

fun main(args : Array<String>) {
    val function = EratosteneFunction()
    function.run(args, { context ->
        function.apply(context.get(EratosteneNumberRequest::class.java), context.get(EratosteneListRequest::class.java))})
}