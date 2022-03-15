package com.sd.laborator.services

import com.sd.laborator.interfaces.HtmlDesignerInterface
import org.springframework.stereotype.Service

@Service
class HtmlDesigner : HtmlDesignerInterface{
    private var dataformat : String = ""
    override fun addHtmlElement(elementType : String, text : String, parameter : Any?){
        dataformat += "<${elementType}>"
        dataformat += "${text} : ${parameter}"
        dataformat += "</${elementType}>"
        dataformat += "\n";
    }

    override fun InitializeHtml(city : String, icon : String){
        dataformat = ""
        dataformat += "<!DOCTYPE html>\n";
        dataformat += "<html>\n";
        dataformat += "<head>\n";
        dataformat += "<title>Vremea in ${city}</title>\n";
        dataformat += "<link rel=\"icon\" href=\"${icon}\"/>\n";
        dataformat += "</head\n";
        dataformat += "<body>\n";
    }

    override fun ProcessHtml() : String{
        dataformat += "</body>\n";
        dataformat += "</html>\n";
        return dataformat;
    }

    override fun addHtmlElement(command : String){
        dataformat += command
        dataformat += "\n"
    }
}