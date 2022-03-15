package com.sd.laborator.services

import com.sd.laborator.interfaces.CheltuieliInterface
import com.sd.laborator.pojo.Detalii
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
@Service
class CheltuieliServices:CheltuieliInterface {
    companion object
    {
        val initialDetalii = arrayOf(
            Detalii(1, "mama", "paine", 1.2, "Luni")
        )
    }
    private val agendaCheltuieli = ConcurrentHashMap<Int, Detalii>(
    initialDetalii.associateBy
    {
        detaliu:Detalii ->detaliu.id
    })

    override fun getDetalii(id: Int): Detalii? {
        return agendaCheltuieli[id]
    }

    override fun createDetalii(detalii: Detalii) {
        /*creez asociere in hash map intre id si detaliu pentru
        * stocarea acestuia*/
       agendaCheltuieli[detalii.id] = detalii
    }

    override fun deleteDetalii(id: Int) {
        if(agendaCheltuieli[id]!=null)
        {
            /*daca exista o inregistrare cu acest id este sters*/
            agendaCheltuieli.remove(id)
        }
    }



    override fun searchDetalii(persoanaNume: String, obiect: String, pret: Double): List<Detalii> {

        return agendaCheltuieli.filter {
            it.value.obiect.contains(obiect, ignoreCase = true) &&
                    it.value.persoana.contains(persoanaNume, ignoreCase = true)

        }. map {
            it.value
        }.toList()

    }

    override fun updateDetalii(id: Int, detalii: Detalii) {
        deleteDetalii(id) /*sterg ce aveam inainte*/
        createDetalii(detalii) /*creez un nou obiect cu ce este nou*/
    }

}