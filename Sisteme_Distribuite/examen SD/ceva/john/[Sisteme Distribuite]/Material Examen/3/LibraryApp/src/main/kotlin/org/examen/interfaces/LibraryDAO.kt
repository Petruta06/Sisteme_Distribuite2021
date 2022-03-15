package org.examen.interfaces

import org.examen.model.Book

interface LibraryDAO {
    fun getBooks() : Set<Book>
    fun addBook(book : Book)
    fun findAllByAuthor(author : String) : Set<Book>
    fun findAllByTitle(title : String) : Set<Book>
    fun findAllByPublisher(publisher : String) : Set<Book>
}