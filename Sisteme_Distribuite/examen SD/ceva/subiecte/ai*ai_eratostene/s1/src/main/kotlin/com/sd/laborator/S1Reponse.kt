package com.sd.laborator

class S1Reponse {
    private var message: String? = null
    private var elements: List<Int>? = null
    fun getElements(): List<Int>? {
        return elements
    }
    fun setElements(elements: List<Int>?) {
        this.elements = elements
    }
    fun getMessage(): String? {
        return message
    }
    fun setMessage(message: String?) {
        this.message = message
    }
}