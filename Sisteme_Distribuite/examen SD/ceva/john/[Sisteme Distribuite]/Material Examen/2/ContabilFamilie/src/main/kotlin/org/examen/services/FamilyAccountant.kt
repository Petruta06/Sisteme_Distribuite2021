package org.examen.services

import org.examen.interfaces.FamilyService
import org.examen.pojo.Member
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureNanoTime

@Service
class FamilyAccountant : FamilyService {
    companion object {
        var family : MutableList<Member> = arrayListOf(
            Member(1, "Roxana", 5000.0, 0.0, ConcurrentHashMap()),
            Member(2, "Stefan", 5000.0, 0.0, ConcurrentHashMap()),
            Member(3, "Elena", 5000.0, 0.0, ConcurrentHashMap()),
            Member(4, "Vasile", 5000.0, 0.0, ConcurrentHashMap()),
            Member(5, "Cristian", 5000.0, 0.0, ConcurrentHashMap()),
            Member(6, "Vasilica", 5000.0, 0.0, ConcurrentHashMap())
        )
    }

    override fun getMember(id: Int): Member? {
        return family[memberIndex(id)]
    }

    override fun addMember(member: Member){
        family.add(member)
    }

    override fun killMember(id: Int){
        family.remove(family.find { it.id == id })
    }

    override fun addVenit(id: Int, venit: Double){
        family[memberIndex(id)].venit += venit
    }

    override fun addCheltuiala(id: Int, cheltuiala: Pair<String, Double>){
        family[memberIndex(id)].listaCheltuieli[cheltuiala.first] = cheltuiala.second
        family[memberIndex(id)].cheltuieli += cheltuiala.second
    }

    override fun getVenit(id: Int): Double {
        return family[memberIndex(id)].venit
    }

    override fun getCheltuieli(id: Int): Double {
        return family[memberIndex(id)].cheltuieli
    }

    override fun calculeazaVenitTotalFamilie(): Double {
        var total : Double = 0.0
        family.forEach{total += it.venit }
        return total
    }

    override fun calculeazaCheltuieliTotaleFamilie(): Double {
        var total : Double = 0.0
        family.forEach{total += it.cheltuieli }
        return total
    }

    override fun memberinFamily(id: Int): Boolean {
        return !family.filter{it.id == id}.isEmpty()
    }

    override fun memberIndex(id: Int): Int {
        return family.indexOf(family.find { it.id == id })
    }

    override fun getFamily(): List<Member> {
        return family.toList()
    }

}