package com.sd.laborator.interfaces

interface HtmlDesignerInterface {
    fun InitializeHtml(city : String, icon : String)
    fun ProcessHtml() : String
    fun addHtmlElement(elementType : String, text : String, parameter : Any?)
    fun addHtmlElement(command : String)
}