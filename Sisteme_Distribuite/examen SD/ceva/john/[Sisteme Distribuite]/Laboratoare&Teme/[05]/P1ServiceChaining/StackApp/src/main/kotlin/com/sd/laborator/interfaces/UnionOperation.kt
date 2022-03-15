package com.sd.laborator.interfaces

import com.sd.laborator.model.Stack

interface UnionOperation {
    fun executeOperation(A: Stack, B: Stack): Set<Pair<Int, Int>>
}