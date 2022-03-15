package org.examen.services

import org.examen.interfaces.LibraryDAO
import org.examen.model.Book
import org.examen.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.sql.SQLException
import java.util.regex.Pattern

class BookRowMapper : RowMapper<Book?> {
    @Throws(SQLException::class)
    override fun mapRow(rs : ResultSet, rowNum : Int) : Book{
        return Book(Content(rs.getString("author"), rs.getString("title"), rs.getString("publisher"), rs.getString("text")))
    }
}

@Service
class LibrariDAOSQLService : LibraryDAO {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    var pattern : Pattern = Pattern.compile("\\W")

    override fun createBookTable(){
        var sqlStatement : String = "CREATE TABLE IF NOT EXISTS books("
        sqlStatement += "id INTEGER PRIMARY KEY AUTOINCREMENT, "
        sqlStatement += "title VARCHAR(50) UNIQUE, "
        sqlStatement += "author VARCHAR(50) UNIQUE, "
        sqlStatement += "publisher VARCHAR(50) UNIQUE, "
        sqlStatement += "text VARCHAR(1000) UNIQUE"
        sqlStatement += ")"
        jdbcTemplate.execute("""${sqlStatement}""")
    }

    override fun getBooks(): Set<Book> {
        var sqlStatement : String = "SELECT * FROM books"
        val result : MutableList<Book?> = jdbcTemplate.query(sqlStatement, BookRowMapper())
        var set : MutableSet<Book> = mutableSetOf()
        result.forEach{set.add(it!!)}
        return set.toSet()
    }

    override fun addBook(book: Book) {
        var sqlStatement : String = "INSERT INTO books(author, title, publisher, text) VALUES (?, ?, ?, ?)"
        jdbcTemplate.update(sqlStatement,book.name, book.author, book.publisher, book.content)
    }

    override fun findAllByAuthor(author: String): Set<Book> {
        var sqlStatement : String = "SELECT * FROM books WHERE author = '$author'"
        var result : MutableList<Book?> = jdbcTemplate.query(sqlStatement, BookRowMapper())
        var set : MutableSet<Book> = mutableSetOf()
        result.forEach{set.add(it!!)}
        return set.toSet()
    }

    override fun findAllByTitle(title: String): Set<Book> {
        var sqlStatement : String = "SELECT * FROM books WHERE title = '$title'"
        var result : MutableList<Book?> = jdbcTemplate.query(sqlStatement, BookRowMapper())
        var set : MutableSet<Book> = mutableSetOf()
        result.forEach{set.add(it!!)}
        return set.toSet()
    }

    override fun findAllByPublisher(publisher: String): Set<Book> {
        var sqlStatement : String = "SELECT * FROM books WHERE publisher = '$publisher'"
        var result : MutableList<Book?> = jdbcTemplate.query(sqlStatement, BookRowMapper())
        var set : MutableSet<Book> = mutableSetOf()
        result.forEach{set.add(it!!)}
        return set.toSet()
    }
}