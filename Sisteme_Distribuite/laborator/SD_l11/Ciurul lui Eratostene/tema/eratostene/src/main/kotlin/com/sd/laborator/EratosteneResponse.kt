package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class EratosteneResponse {
    private var message: String? = null
    private var primes: List<Int>? = null
    fun getPrimes(): List<Int>? {
        return primes
    }

    fun setPrimes(primes: List<Int>?, list:List<Int>) {

        if(!primes.isNullOrEmpty())
        {
            val p = List<Int>(0){0}
            for(x in list)
            {
                if(isInList(primes, x)==1)
                {
                    p += x
                }
            }
            this.primes = p
        }


    }
    fun getMessage(): String? {
        return message
    }
    fun setMessage(message: String?) {
        this.message = message
    }
    fun isInList(list:List<Int>, c:Int):Int
    {
        for(x in list)
        {
            if(x==c)
                return 1
        }
        return 0
    }
}