package org.examen.interfaces

import org.examen.model.Book

interface JSONPrinter {
    fun printJSON(books : Set<Book>) : String
}