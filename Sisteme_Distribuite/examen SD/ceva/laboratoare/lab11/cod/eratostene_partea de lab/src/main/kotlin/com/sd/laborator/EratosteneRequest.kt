package com.sd.laborator

import io.micronaut.core.annotation.*

@Introspected
class EratosteneRequest {
	private lateinit var number:Integer
	private lateinit var first:Integer

	fun getNumber():Int{
		return number.toInt()
	}

	fun getFirst():Int{
		return first.toInt()
	}



}


