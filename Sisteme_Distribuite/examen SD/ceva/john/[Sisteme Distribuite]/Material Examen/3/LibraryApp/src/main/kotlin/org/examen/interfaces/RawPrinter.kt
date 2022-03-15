package org.examen.interfaces

import org.examen.model.Book

interface RawPrinter{
    fun printRaw(books : Set<Book>) : String
}