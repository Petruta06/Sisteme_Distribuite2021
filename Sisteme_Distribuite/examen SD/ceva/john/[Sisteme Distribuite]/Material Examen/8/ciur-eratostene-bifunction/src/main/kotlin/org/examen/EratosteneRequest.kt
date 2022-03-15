package org.examen

import io.micronaut.core.annotation.*
import javax.print.attribute.IntegerSyntax

@Introspected
class EratosteneRequest {
	private lateinit var number : Integer
	private lateinit var candidates : List<Integer>

	fun getNumber() : Int{
		return number.toInt()
	}

	fun getCandidates() : List<Int>{
		var list : MutableList<Int> = mutableListOf()
		candidates.forEach {
			list.add(it.toInt())
		}
		return list.toList()
	}
}


