package com.sd.laborator.services

import com.sd.laborator.interfaces.PrimeNumberGenerator
import com.sd.laborator.interfaces.SetGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.sd.laborator.model.Stack

@Service
class SetGeneratorService : SetGenerator{
    @Autowired
    private lateinit var primeNumberGenerator: PrimeNumberGenerator
    override fun setGenerate(length: Int): Stack {
        if (length < 1)
            return null!!
        var X: MutableSet<Int> = mutableSetOf()
        while (X.count() < length)
            X.add(primeNumberGenerator.generatePrimeNumber())
        return Stack(X)
    }
}