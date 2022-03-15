package org.examen

import io.micronaut.core.annotation.Introspected

@Introspected
class Content {
    private lateinit var content : String

    fun getContent() = this.content
}