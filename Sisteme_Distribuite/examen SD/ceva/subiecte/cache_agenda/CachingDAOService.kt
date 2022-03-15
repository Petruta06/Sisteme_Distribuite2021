package com.sd.laborator.services

import com.sd.laborator.interfaces.CachingDAO
import com.sd.laborator.model.Cache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

@Service
class CachingDAOService : CachingDAO {
    private val cacheSet:MutableSet<Cache> = mutableSetOf()

    override fun exists(query: String): String? {
        val timestamp:Timestamp = Timestamp(System.currentTimeMillis())
        val getQuerys = cacheSet.filter { it.query == query }
        if (getQuerys.isEmpty())
            return null

        var timestampMax:Timestamp? = null
        var ret:String? = null
        getQuerys.forEach{
            if(timestampMax == null) {
                timestampMax = it.timestamp
                ret = it.result
            }
            else{
                if(timestampMax?.time!! > it.timestamp.time){
                    timestampMax = it.timestamp
                    ret = it.result
                }
            }
        }
        if(timestampMax == null) return null
        // timestamp difference

        val miliseconds = timestamp.time - timestampMax!!.time
        val seconds = miliseconds/1000
        val hours = seconds/3600

        if(hours >= 1)
            return null

        return ret
    }

    override fun addToCache(query: String, result: String) {
        val timestamp:Timestamp = Timestamp(System.currentTimeMillis())
        cacheSet.add(Cache(timestamp, query, result))
    }
}