package com.sd.laborator.interfaces
import com.sd.laborator.pojo.Book
interface HTMLPrinter {
    fun printHTML(books: Set<Book>): String
}