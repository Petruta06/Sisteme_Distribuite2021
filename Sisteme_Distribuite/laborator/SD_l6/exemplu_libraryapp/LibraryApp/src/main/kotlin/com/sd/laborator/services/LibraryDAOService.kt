package com.sd.laborator.services

import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.util.regex.Pattern


class LibaryRowMapper : RowMapper<Book?> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Book {
       return Book(Content(rs.getInt("id"), rs.getString("author"),rs.getString("title"),rs.getString("publisher"),  rs.getString("text") ))
    }
}


@Service
class LibraryDAOService: LibraryDAO {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    var pattern: Pattern = Pattern.compile("\\W")
    /*private var books: MutableSet<Book> = mutableSetOf(
        Book(Content("Roberto Ierusalimschy","Preface. When Waldemar, Luiz, and I started the development of Lua, back in 1993, we could hardly imagine that it would spread as it did. ...","Programming in LUA","Teora")),
        Book(Content("Jules Verne","Nemaipomeniti sunt francezii astia! - Vorbiti, domnule, va ascult! ....","Steaua Sudului","Corint")),
        Book(Content("Jules Verne","Cuvant Inainte. Imaginatia copiilor - zicea un mare poet romantic spaniol - este asemenea unui cal nazdravan, iar curiozitatea lor e pintenul ce-l fugareste prin lumea celor mai indraznete proiecte.","O calatorie spre centrul pamantului","Polirom")),
        Book(Content("Jules Verne","Partea intai. Naufragiatii vazduhului. Capitolul 1. Uraganul din 1865. ...","Insula Misterioasa","Teora")),
        Book(Content("Jules Verne","Capitolul I. S-a pus un premiu pe capul unui om. Se ofera premiu de 2000 de lire ...","Casa cu aburi","Albatros"))
    )*/


    override fun createTable() {
        jdbcTemplate.execute("""CREATE TABLE IF NOT EXISTS books(
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       author VARCHAR(100), 
                                       title VARCHAR(100), 
                                       publisher VARCHAR(100), 
                                        text TEXT)""")

        jdbcTemplate.execute("INSERT INTO books VALUES (1, 'Roberto Ierusalimschy', 'Programming in LUA', 'Teora', 'Preface. When Waldemar, Luiz, and I started the development of Lua, back in 1993, we could hardly imagine that it would spread as it did. ...')")

    }
    override fun getBooks(): Set<Book?> {
        //interoghez bd si scot lista cu carti din aceasta
        val result: MutableList<Book?> = jdbcTemplate.query("SELECT * FROM books ", LibaryRowMapper())
        //transform totul sub un sir de caractere
        val set : MutableSet<Book?> = mutableSetOf<Book?>()

        for(c in result)
        {
            set.add(c)
        }
        return set
    }

    override fun addBook(book: Book) {
        if(pattern.matcher(book.name).find())
        {
            println("DE INTELES DE CE FACE ASA")
            return
        }

    }

    override fun findAllByAuthor(author: String):Set<Book?>  {
        val result: MutableList<Book?> = jdbcTemplate.query("SELECT * FROM books where author = '$author'", LibaryRowMapper())

        val set : MutableSet<Book?> = mutableSetOf<Book?>()

        for(c in result)
        {
            set.add(c)
        }
        return set


    }

    override fun findAllByTitle(title: String): Set<Book?> {
        val set : MutableSet<Book?> = mutableSetOf<Book?>()
        if(pattern.matcher(title).find())
        {
            println("DE INTELES DE CE FACE ASA")
            return set
        }
        var result :Book? = jdbcTemplate.queryForObject("SELECT * FROM books where title = '$title'",LibaryRowMapper() )
        set.add(result)
        return set

    }

    override fun findAllByPublisher(publisher: String): Set<Book?> {
        val set : MutableSet<Book?> = mutableSetOf<Book?>()
        if(pattern.matcher(publisher).find())
        {
            println("DE INTELES DE CE FACE ASA")
            return set
        }

        var result :Book? = jdbcTemplate.queryForObject("SELECT * FROM books where publisher = '$publisher'",LibaryRowMapper() )
        set.add(result)
        return set
    }
}