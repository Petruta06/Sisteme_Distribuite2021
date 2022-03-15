package com.sd.laborator.interfaces

import com.sd.laborator.pojo.Detalii

interface CheltuieliInterface {
    fun getDetalii (id:Int): Detalii?
    fun createDetalii (detalii: Detalii)
    fun deleteDetalii(id:Int)
    fun updateDetalii(id:Int, detalii: Detalii)
    fun searchDetalii(
        persoanaNume:String, obiect:String,
        pret:Double
    ):List<Detalii>

}