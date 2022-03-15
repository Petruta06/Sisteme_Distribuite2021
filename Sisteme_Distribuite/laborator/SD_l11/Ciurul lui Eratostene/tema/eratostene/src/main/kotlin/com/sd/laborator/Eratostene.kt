package com.sd.laborator

import io.micronaut.core.annotation.*

@Introspected
class EratosteneRequest {
	private lateinit var number: Integer
	private lateinit var list :List<Integer>
	fun getList():List<Int>
	{
		var x=List<Int>(0){0}
		var i=0
		for(c in list)
			x +=c.toInt()
		return x
	}
	fun getNumber(): Int {
		return number.toInt()
	}

}

