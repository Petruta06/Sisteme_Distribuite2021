package com.sd.laborator

import io.micronaut.core.annotation.*
import javax.print.attribute.HashAttributeSet
import kotlin.collections.HashMap

@Introspected
class Consumer {
	var contentPair: MutableList<HashMap<String, String>>

	init{
		this.contentPair = mutableListOf()
	}

	fun AddPair(pair1 : Pair<String, String>, pair2 : Pair<String, String>){
		val hMAp = hashMapOf<String, String>()
		hMAp[pair1.first] = pair1.second
		hMAp[pair2.first] = pair2.second
		contentPair.add(hMAp)

	}

	fun GetContent() : Consumer{
		return this;
	}
}


