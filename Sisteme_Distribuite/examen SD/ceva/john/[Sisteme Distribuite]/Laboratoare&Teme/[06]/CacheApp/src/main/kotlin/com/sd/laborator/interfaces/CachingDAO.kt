package com.sd.laborator.interfaces

interface CachingDAO {
    fun exists(query : String) : String?
    fun addToCache(query: String,time : String, result : String) : String
}