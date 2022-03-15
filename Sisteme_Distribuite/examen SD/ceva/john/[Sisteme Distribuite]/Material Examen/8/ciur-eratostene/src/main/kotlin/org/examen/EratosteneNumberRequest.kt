package org.examen

import io.micronaut.core.annotation.*
import javax.print.attribute.IntegerSyntax

@Introspected
class EratosteneNumberRequest {
	private lateinit var number : Integer

	fun getNumber() : Int{
		return number.toInt()
	}
}


