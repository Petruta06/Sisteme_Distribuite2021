package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class EratosteneResponse {
    private var message: String? = null
   private var primes: HashMap<Int, Int>? = null
    fun getPrimes(): HashMap<Int, Int>? {
        return primes
    }
    fun setPrimes(primes: HashMap<Int, Int>?) {
        this.primes = primes
    }
    fun getMessage(): String? {
        return message
    }
    fun setMessage(message: String?) {
        this.message = message
    }
}