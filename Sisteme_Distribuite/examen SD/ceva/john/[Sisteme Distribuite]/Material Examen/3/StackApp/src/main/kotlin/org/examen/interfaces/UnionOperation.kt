package org.examen.interfaces

interface UnionOperation{
    fun executeOperation(A : Set<Pair<Int, Int>>, B : Set<Pair<Int, Int>>) : Set<Pair<Int, Int>>
}