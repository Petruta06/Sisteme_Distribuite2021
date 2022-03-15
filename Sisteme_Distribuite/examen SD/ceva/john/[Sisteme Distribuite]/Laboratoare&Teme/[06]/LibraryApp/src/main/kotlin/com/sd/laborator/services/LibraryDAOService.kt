package com.sd.laborator.services

import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.sql.SQLException
import java.util.regex.Pattern
import kotlin.collections.*

class BookRowMapper : RowMapper<Book?>{
    @Throws(SQLException::class)
    override fun mapRow(result: ResultSet, rowNumber: Int): Book? {
        return Book(Content(result.getString("name"),result.getString("author"),
                            result.getString("publisher"), result.getString("content")))
    }
}

@Service
class LibraryDAOService: LibraryDAO {
    @Autowired
    private lateinit var jdbcTemplate : JdbcTemplate
    var pattern : Pattern = Pattern.compile("\\W")
    override fun createBooksTable(){
        jdbcTemplate.execute("""CREATE TABLE IF NOT EXISTS books(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50) NOT NULL, author VARCHAR(30) NOT NULL, publisher VARCHAR(20) NOT NULL, content VARCHAR(200))""")
        this.addBook(Book(Content("Roberto Ierusalimschy","Preface. When Waldemar, Luiz, and I started the development of Lua, back in 1993, we could hardly imagine that it would spread as it did. ...","Programming in LUA","Teora")))
        this.addBook(Book(Content("Jules Verne","Nemaipomeniti sunt francezii astia! - Vorbiti, domnule, va ascult! ....","Steaua Sudului","Corint")))
        this.addBook(Book(Content("Jules Verne","Cuvant Inainte. Imaginatia copiilor - zicea un mare poet romantic spaniol - este asemenea unui cal nazdravan, iar curiozitatea lor e pintenul ce-l fugareste prin lumea celor mai indraznete proiecte.","O calatorie spre centrul pamantului","Polirom")))
        this.addBook(Book(Content("Jules Verne","Partea intai. Naufragiatii vazduhului. Capitolul 1. Uraganul din 1865. ...","Insula Misterioasa","Teora")))
        this.addBook(Book(Content("Jules Verne","Capitolul I. S-a pus un premiu pe capul unui om. Se ofera premiu de 2000 de lire ...","Casa cu aburi","Albatros")))
    }
    override fun cleanBooksTable() {
        jdbcTemplate.execute("DELETE FROM books")
    }

    override fun getBooks(): Set<Book?> {
        val result : MutableList<Book?> = jdbcTemplate.query("SELECT * FROM books",BookRowMapper())
        return result.toSet()
    }

    override fun addBook(book: Book){
        jdbcTemplate.update("INSERT INTO books(name, author, publisher, content) VALUES (?, ?, ?, ?)",
            book.name, book.author, book.publisher, book.content)
    }

    override fun findAllByAuthor(author: String): Set<Book?> {
        val result : MutableList<Book?> = jdbcTemplate.query("SELECT * FROM books where author = '$author'", BookRowMapper())
        return result.toSet()
    }

    override fun findAllByTitle(title: String): Set<Book?> {
        val result : MutableList<Book?> = jdbcTemplate.query("SELECT * FROM books where name = '$title'", BookRowMapper())
        return result.toSet()
    }

    override fun findAllByPublisher(publisher: String): Set<Book?> {
        val result : MutableList<Book?> = jdbcTemplate.query("SELECT * FROM books where publisher = '$publisher'", BookRowMapper())
        return result.toSet()
    }
}