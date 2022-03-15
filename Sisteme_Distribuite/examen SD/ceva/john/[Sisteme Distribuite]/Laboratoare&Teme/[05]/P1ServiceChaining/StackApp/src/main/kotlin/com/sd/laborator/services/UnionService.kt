package com.sd.laborator.services

import com.sd.laborator.interfaces.CartesianProductOperation
import com.sd.laborator.interfaces.UnionOperation
import com.sd.laborator.model.Stack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UnionService: UnionOperation {
    @Autowired
    private lateinit var cartesianProductOperation: CartesianProductOperation
    override fun executeOperation(A: Stack, B: Stack): Set<Pair<Int, Int>> {
        // (A x B) U (B x B)
        val partialResult1 = cartesianProductOperation.executeOperation(A!!.data, B!!.data)
        val partialResult2 = cartesianProductOperation.executeOperation(B!!.data, B!!.data)
        return partialResult1 union partialResult2
    }

}