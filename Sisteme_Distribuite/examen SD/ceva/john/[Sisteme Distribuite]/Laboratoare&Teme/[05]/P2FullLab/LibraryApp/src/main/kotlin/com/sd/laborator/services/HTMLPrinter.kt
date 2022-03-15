package com.sd.laborator.services

import com.sd.laborator.model.Book

class HTMLPrinter{
    companion object{
        fun printer(books : Set<Book>) : String{
            var content: String = "<html><head><title>Libraria mea HTML</title></head><body>"
            books.forEach {
                content += "<p><h3>${it.name}</h3><h4>${it.author}</h4><h5>${it.publisher}</h5>${it.content}</p><br/>"
            }
            content += "</body></html>"
            return content
        }
    }
}
