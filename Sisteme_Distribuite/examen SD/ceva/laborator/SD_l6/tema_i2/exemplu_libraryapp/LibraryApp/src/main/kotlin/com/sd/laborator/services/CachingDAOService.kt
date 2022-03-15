package com.sd.laborator.services

import com.sd.laborator.interfaces.CachingDAO
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern



class CachingRowMapper : RowMapper<String?> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): String?  {
        return rs.getInt("timestamp").toString() + '\n' + rs.getString("result")+ '\n'+ rs.getString("query")
    }
}


@Service
class CachingDAOService:CachingDAO {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    var pattern: Pattern = Pattern.compile("\\W")

    override fun createCacheTable() {
        jdbcTemplate.execute("""CREATE TABLE IF NOT EXISTS cache(
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        timestamp INTEGER, 
                                        query VARCHAR(100), 
                                        result VARCHAR(100)""")
    }


    override fun addToCache(query: String, result: String):String? {

        this.createCacheTable()
        var currentDateTime= LocalDateTime.now()
        var ora =   (currentDateTime.format(DateTimeFormatter.ofPattern("HH"))).toInt()
        var minut =   currentDateTime.format(DateTimeFormatter.ofPattern("mm")).toInt()
        var moment_inregistrare = ora*60 + minut
        //val sql = "INSERT INTO $schema.$table VALUES ($id, $name, $price)
        jdbcTemplate.update("""INSERT INTO CACHE (timestamp,query, result) VALUES   (?, ?,?, ?) """, moment_inregistrare, query, result)
        return "Inserare cu succes!"
    }

    override fun exist(query: String): String? {
        /*interoghez bd pentru a vedea daca exista inregistrarea*/
        val result = jdbcTemplate.query("SELECT * FROM cache WHERE query =$query", CachingRowMapper())
        var stringResult: String = ""
        if(result != null)
        {
            return "exists \n   ${result}"
        }
        else
        {
            return "nu exista \n ${query}"
        }
    }

}