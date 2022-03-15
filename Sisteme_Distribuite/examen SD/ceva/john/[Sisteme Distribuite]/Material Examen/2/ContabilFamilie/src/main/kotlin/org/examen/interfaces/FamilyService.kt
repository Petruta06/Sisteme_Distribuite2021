package org.examen.interfaces

import org.examen.pojo.Member

interface FamilyService{
    fun getMember(id : Int) : Member?
    fun addMember(member: Member)
    fun killMember(id : Int)
    fun addVenit(id : Int, venit : Double)
    fun addCheltuiala(id : Int, cheltuiala : Pair<String, Double>)
    fun getVenit(id : Int) : Double
    fun getCheltuieli(id : Int) : Double
    fun calculeazaVenitTotalFamilie() : Double
    fun calculeazaCheltuieliTotaleFamilie() : Double
    fun memberinFamily(id : Int) : Boolean
    fun memberIndex(id : Int) : Int
    fun getFamily() : List<Member>
}