package org.examen

import io.micronaut.core.annotation.*

@Introspected
class Producer {
	private lateinit var url: String

	fun getUrl() : String{
		return url
	}
}


