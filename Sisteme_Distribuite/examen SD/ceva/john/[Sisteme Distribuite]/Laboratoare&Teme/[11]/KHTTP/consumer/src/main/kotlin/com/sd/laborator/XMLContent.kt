package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class XMLContent {
    lateinit var xmlContent: String

    fun GetContent() : String{
        return this.xmlContent
    }
}