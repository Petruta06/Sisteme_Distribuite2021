package com.sd.laborator.services

import com.sd.laborator.model.Book

class JSONPrinter{
    companion object{
        fun printer(books : Set<Book>) : String{
            var content: String = "[\n"
            books.forEach {
                if (it != books.last())
                    content += "    {\"Titlu\": \"${it.name}\", \"Autor\":\"${it.author}\", \"Editura\":\"${it.publisher}\", \"Text\":\"${it.content}\"},\n"
                else
                    content += "    {\"Titlu\": \"${it.name}\", \"Autor\":\"${it.author}\", \"Editura\":\"${it.publisher}\", \"Text\":\"${it.content}\"}\n"
            }
            content += "]\n"
            return content
        }
    }
}