package org.examen.interfaces

interface CartesianProductOperation{
    fun executeOperation(A : Set<Int>, B : Set<Int>) : Set<Pair<Int, Int>>
}