package com.sd.laborator.interfaces

import com.sd.laborator.pojo.Book

interface JSONPrinter {
    fun printJSON(books: Set<Book>): String
}