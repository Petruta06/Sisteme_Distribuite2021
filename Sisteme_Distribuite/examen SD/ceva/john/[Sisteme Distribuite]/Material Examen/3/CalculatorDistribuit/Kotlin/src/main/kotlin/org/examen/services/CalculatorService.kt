package org.examen.services

import org.examen.interfaces.CalculatorInterface
import org.springframework.stereotype.Service
import javax.script.ScriptEngineManager

@Service
class CalculatorService : CalculatorInterface{
//    private val engine = ScriptEngineManager().getEngineByName("js")!!
    override fun computeExpression(expression: String): String{
        //return engine.eval(expression).toString()
        return "Ati introdus expresia ${expression}! Scuze, dar nu stiu sa calculez :("
    }
}