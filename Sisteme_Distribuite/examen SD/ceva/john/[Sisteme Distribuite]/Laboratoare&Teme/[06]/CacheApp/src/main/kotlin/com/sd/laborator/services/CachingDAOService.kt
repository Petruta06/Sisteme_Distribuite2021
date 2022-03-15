package com.sd.laborator.services

import com.sd.laborator.interfaces.CachingDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.util.regex.Pattern

class CacheRowMapper : RowMapper<String?> {
    @Throws(SQLException::class)
    override fun mapRow(result: ResultSet, rowNumber: Int): String? {
        return result.getString("query") + "," + result.getString("time") + "," + result.getString("result")
    }
}

@Service
class CachingDAOService : CachingDAO{
    @Autowired
    private lateinit var jdbcTemplate : JdbcTemplate
    var pattern : Pattern = Pattern.compile("\\W")

    override fun addToCache(query: String,time : String, result: String) : String {
        jdbcTemplate.execute("""CREATE TABLE IF NOT EXISTS cache(id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp VARCHAR(10) NOT NULL, query VARCHAR(50) NOT NULL, result VARCHAR(100) NOT NULL)""")
        jdbcTemplate.update("INSERT INTO cache(timestamp, query, result) VALUES (?, ?, ?)",
            time,query,result)
        return "Inserted!"
    }

    override fun exists(query: String): String? {
        val result  = jdbcTemplate.query("SELECT * FROM cache where query = '$query'", CacheRowMapper())
        if(result.contains(",")){
            return "exists [${result}]"
        }
        else{
            return "not found [${query}]"
        }
    }
}