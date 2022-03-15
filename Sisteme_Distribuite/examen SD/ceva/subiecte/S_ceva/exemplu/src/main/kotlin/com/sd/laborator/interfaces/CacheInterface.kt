package com.sd.laborator.interfaces

import com.sd.laborator.model.Book

interface CacheInterface {
    fun updateInformation(book: Book)
    fun writeInformation(book:Book)
    fun findAllByAuthor(author: String): Set<Book>
    fun deleteInformation(book: Book)
    fun writeAll(books:Set<Book>)
    fun updateAll(books:Set<Book>)
}