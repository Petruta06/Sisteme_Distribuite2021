package com.sd.laborator.services

import com.sd.laborator.interfaces.AccountantService
import com.sd.laborator.pojo.Member
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class AccountantServiceImpl : AccountantService {
    companion object{
        val initialFamily = arrayOf(
            Member(1, "Vasile",   12000.0, 500.0),
            Member(2, "Elena",    3500.0,  800.0),
            Member(3, "Cristian", 8000.0,  2500.0),
            Member(4, "Roxana",   1200.0,  500.2),
            Member(5, "Stefan",   2800.0,  1200.0)
        )
    }

    private val family = ConcurrentHashMap<Int, Member>(
        initialFamily.associateBy { member : Member -> member.id }
    )

    override fun getMember(id : Int) : Member? {
        return family[id];
    }

    override fun createMember(member: Member){
        family[member.id] = member;
    }

    override fun deleteMember(id : Int){
        family.remove(id);
    }

    override fun updateMember(id : Int, member : Member){
        deleteMember(id)
        createMember(member)
    }

    override fun calculateBugdet() : Double{
        var budget : Double = 0.0;
        for(member in family.entries){
            budget = budget + member.value.budget
        }
        return budget;
    }

    override fun calculateOutGo() : Double{
        var outgo : Double = 0.0;
        for(member in family.entries){
            outgo = outgo + member.value.outgo
        }
        return outgo;
    }

    override fun searchFamily(NameFilter : String) : List<Member>{
        return family.filter {
                it.value.name.toLowerCase().contains(NameFilter,ignoreCase = true)
            }.map {
                it.value
        }.toList()
    }
}