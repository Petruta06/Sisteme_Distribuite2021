package com.sd.laborator.interfaces

import com.sd.laborator.pojo.Person

interface CacheInterface {
     fun search(s:String):List<Person>
     fun write(s:List<Person>)

     fun update(s:Person)
     fun read():String

     fun isValid(s:String):Boolean

}