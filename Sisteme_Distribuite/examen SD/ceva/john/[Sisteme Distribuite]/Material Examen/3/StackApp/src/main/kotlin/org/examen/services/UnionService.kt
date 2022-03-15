package org.examen.services

import org.examen.interfaces.UnionOperation
import org.springframework.stereotype.Service

@Service
class UnionService : UnionOperation{
    override fun executeOperation(A: Set<Pair<Int, Int>>, B: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        return A union B
    }
}