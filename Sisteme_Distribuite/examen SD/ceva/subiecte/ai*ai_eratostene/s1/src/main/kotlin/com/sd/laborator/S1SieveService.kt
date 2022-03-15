package com.sd.laborator

import javax.inject.Singleton
import kotlin.random.Random
@Singleton
class S1SieveService {

    fun calculeaza(list:List<Int>):List<Int>
    {
        var result:List<Int> = ArrayList()
        var suma = 0
        for (i in 1 until list.size)
        {
            suma = suma + list[i]*list[i]
            result += suma
        }
        return result

    }
    fun generate(n:Int):List<Int>
    {
        var result:List<Int> = ArrayList()

        for( i in 0 until n)
        {
            var k = Random.nextInt(0,100)
            result += k
        }

        return result
    }
}