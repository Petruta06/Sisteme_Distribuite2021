package org.examen

import io.micronaut.core.annotation.Introspected

@Introspected
class Consumer {
    private lateinit var content : String

    fun getContent() : String = this.content
    fun setContent(content : String){
        this.content = content
    }
}