package com.sd.laborator.interfaces

import com.sd.laborator.pojo.Member

interface AccountantService {
    fun getMember(id : Int) : Member?
    fun createMember(member: Member)
    fun deleteMember(id : Int)
    fun updateMember(id : Int, member : Member)
    fun calculateBugdet() : Double
    fun calculateOutGo() : Double
    fun searchFamily(NameFilter : String) : List<Member>
}