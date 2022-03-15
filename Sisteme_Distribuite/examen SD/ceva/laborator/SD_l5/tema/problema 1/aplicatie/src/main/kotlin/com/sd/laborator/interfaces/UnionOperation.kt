package com.sd.laborator.interfaces

interface UnionOperation {
    fun executeOperation(A: Set<Int>, B: Set<Int>): Set<Pair<Int, Int>>
}