package org.examen

import io.micronaut.core.annotation.Introspected

@Introspected
class EratosteneListRequest {
    private lateinit var candidates : List<Integer>

    fun getCandidates() : List<Int>{
        var list : MutableList<Int> = mutableListOf()
        candidates.forEach {
            list.add(it.toInt())
        }
        return list.toList()
    }
}