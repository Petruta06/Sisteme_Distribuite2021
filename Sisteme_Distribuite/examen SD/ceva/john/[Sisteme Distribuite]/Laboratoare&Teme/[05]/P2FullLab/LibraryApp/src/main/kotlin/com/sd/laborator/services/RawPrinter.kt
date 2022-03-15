package com.sd.laborator.services

import com.sd.laborator.model.Book

class RawPrinter{
    companion object{
        fun printer(books : Set<Book>) : String{
            var content: String = ""
            books.forEach {
                content += "${it.name}\n${it.author}\n${it.publisher}\n${it.content}\n\n"
            }
            return content
        }
    }
}