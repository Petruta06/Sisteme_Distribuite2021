package org.examen.interfaces

import org.examen.model.Book

interface HTMLPrinter {
    fun printHTML(books : Set<Book>) : String
}