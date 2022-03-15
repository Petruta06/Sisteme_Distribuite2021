package org.examen.services

import org.examen.interfaces.ParserInterface
import org.springframework.stereotype.Service

@Service
class ExpressionParser : ParserInterface{
    override fun parseExpression(expression: String): String? {
        expression.forEach {
            if(it >= 'A' && it <= 'z'){
                return null
            }
        }
        var expr = expression.replace(" ", "")
        expr = expr.replace(",",".")
        expr = expr.replace("=","")
        return expr
    }
}