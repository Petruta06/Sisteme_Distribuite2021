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


class BD(public var timp:Int?, public var date:String?)
{

}
class CachingRowMapper : RowMapper<BD?> {
    @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): BD   {
            return BD(rs.getInt("timestamp"), rs.getString("result"))
        }
    }

@Service
class CachingDAOService:CachingDAO {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    var pattern: Pattern = Pattern.compile("\\W")

     fun createCacheTable() {
        jdbcTemplate.execute("""CREATE TABLE IF NOT EXISTS cache(
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        timestamp INTEGER, 
                                        query VARCHAR(100), 
                                        result VARCHAR(100)""")
    }


    override fun addToCache(query: String, result: String) {

        var currentDateTime= LocalDateTime.now()
        var ora =   (currentDateTime.format(DateTimeFormatter.ofPattern("HH"))).toInt()
        var minut =   currentDateTime.format(DateTimeFormatter.ofPattern("mm")).toInt()
        var moment_inregistrare = ora*60 + minut
        //val sql = "INSERT INTO $schema.$table VALUES ($id, $name, $price)
        jdbcTemplate.execute("""INSERT INTO CACHE VALUES ($moment_inregistrare, $query,$result, $moment_inregistrare) """)
    }

    override fun exist(query: String): String? {
        /*interoghez bd pentru a vedea daca exista inregistrarea*/
        val result: List<BD?> = jdbcTemplate.query("SELECT * FROM cache WHERE query =$query", CachingRowMapper())
        var stringResult: String = ""
        if (result != null) {
            /*daca exista verific conditia ca timpul scurs sa nu fie >60 mine*/
            var currentDateTime = LocalDateTime.now()
            var ora = (currentDateTime.format(DateTimeFormatter.ofPattern("HH"))).toInt()
            var minut = currentDateTime.format(DateTimeFormatter.ofPattern("mm")).toInt()
            var moment_inregistrare: Int = (ora * 60 + minut)
            if (moment_inregistrare - result.get(0)!!.timp!!.toInt() > 60) {
                /*trebuie sa fac update la ora pentru ca s-a facut o noua cerere*/
                jdbcTemplate.update("UPDATE cache SET timestamp = ? WHERE query = ?", moment_inregistrare, query)
                return "0"
            } else {
                return result.get(0)!!.date!!.toString()

            }

        }
        else
        {
            /*nu am inregistarea in tabel si o adaug si trimit un mesaj corespunzator*/
            //addToCache(query, "")
            return "1"
        }
    }

}