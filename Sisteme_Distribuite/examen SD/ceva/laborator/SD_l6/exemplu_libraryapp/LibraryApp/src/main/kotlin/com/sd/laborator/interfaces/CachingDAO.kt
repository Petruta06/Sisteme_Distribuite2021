package com.sd.laborator.interfaces

interface CachingDAO {

    fun exist (query:String): String?
    fun addToCache(query:String, result:String)
}