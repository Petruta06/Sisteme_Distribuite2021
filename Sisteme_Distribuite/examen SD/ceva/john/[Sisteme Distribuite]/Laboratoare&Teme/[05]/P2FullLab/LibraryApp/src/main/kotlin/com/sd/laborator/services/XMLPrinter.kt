package com.sd.laborator.services

import com.sd.laborator.model.Book

class XMLPrinter{
    companion object{
        fun printer(books : Set<Book>) : String{
            var content: String = "<books>\n";
            books.forEach {
                content += "\t<book>\n"
                content += "\t\t<titlu>${it.name}</title>\n";
                content += "\t\t<autor>${it.author}</autor>\n";
                content += "\t\t<editura>${it.publisher}</editura>\n";
                content += "\t\t<text>${it.content}</text>\n";
                content += "\t</book>\n";
            }
            content += "</books>";
            return content
        }
    }
}