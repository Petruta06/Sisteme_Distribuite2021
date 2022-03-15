package org.examen.interfaces

import org.examen.model.Beer

interface BeerDAO{
    // C
    fun createBeerTable()
    fun addBear(beer: Beer)

    // R
    fun getBeers() : String
    fun getBeerByName(name : String) : String?
    fun getBeerByPrice(price : Float) : String?

    // U
    fun updateBeer(beer : Beer)

    // D
    fun deleteBeer(name : String)
}