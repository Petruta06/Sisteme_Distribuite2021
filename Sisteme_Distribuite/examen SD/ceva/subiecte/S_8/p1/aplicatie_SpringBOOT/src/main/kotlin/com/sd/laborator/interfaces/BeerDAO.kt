package com.sd.laborator.interfaces

import com.sd.laborator.model.Beer

interface BeerDAO {
    public fun createBeerTable()
    public fun addBeer(beer: Beer)
    public fun getBeers():String
    public fun getBeerByName(name:String):String?
    public fun getBeerByPrice(price:Float):String?
    public fun updateBeer(beer:Beer)
    public fun deleteBeer(name:String)

}