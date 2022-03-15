package com.sd.laborator.services

import com.sd.laborator.interfaces.CartesianProductOperation
import com.sd.laborator.interfaces.UnionOperation
import org.springframework.stereotype.Service

@Service
class UnionService: UnionOperation {
    lateinit var cartesianProductOperation:CartesianProductOperation

    override fun executeOperation(A: Set<Int>, B: Set<Int>): Set<Pair<Int, Int>> {
        cartesianProductOperation = CartesianProductService()
        val partialResult1 = cartesianProductOperation.executeOperation(A, B)
        val partialResult2 = cartesianProductOperation.executeOperation(B, B)

        return partialResult1 union partialResult2
    }

}