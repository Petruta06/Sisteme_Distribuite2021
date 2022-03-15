package com.sd.laborator.controllers

import com.sd.laborator.interfaces.AccountantService
import com.sd.laborator.pojo.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class accountantController{
    @Autowired
    private lateinit var accountantService : AccountantService


    @RequestMapping(value = ["/member"], method = [RequestMethod.POST])
    fun createMember(@RequestBody member : Member) : ResponseEntity<Unit>{
        accountantService.createMember(member)
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/member/{id}"], method = [RequestMethod.GET])
    fun getMember(@PathVariable id : Int) : ResponseEntity<Member?>{
        val member : Member? = accountantService.getMember(id)
        val status = if(member == null){
            HttpStatus.NOT_FOUND
        }
        else{
            HttpStatus.OK
        }
        return ResponseEntity(member, status)
    }

    @RequestMapping(value = ["/member/{id}"], method = [RequestMethod.PUT])
    fun updateMember(@PathVariable id : Int, @RequestBody member: Member) : ResponseEntity<Unit>{
        accountantService.getMember(id)?.let {
            accountantService.updateMember(it.id, member)
            return ResponseEntity(Unit, HttpStatus.ACCEPTED)
        } ?: return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value = ["/member/{id}"], method = [RequestMethod.DELETE])
    fun deleteMember(@PathVariable id : Int) : ResponseEntity<Unit>{
        if(accountantService.getMember(id) != null){
            accountantService.deleteMember(id)
            return ResponseEntity(Unit, HttpStatus.OK)
        }
        else{
            return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
        }
    }

    @RequestMapping(value = ["/family"], method = [RequestMethod.GET])
    fun search(@RequestParam(required = false, name = "Name", defaultValue = "") Name : String):
            ResponseEntity<List<Member>>{
        val memberList = accountantService.searchFamily(Name)
        var httpStatus = HttpStatus.OK
        if(memberList.isEmpty()){
            httpStatus = HttpStatus.NO_CONTENT
        }
        return ResponseEntity(memberList, httpStatus)
    }

    @RequestMapping(value = ["/family/budget"], method = [RequestMethod.GET])
    fun getBudget() : ResponseEntity<String>{
        val budget = accountantService.calculateBugdet()
        var httpStatus = HttpStatus.OK
        if(budget == 0.0){
            httpStatus = HttpStatus.NO_CONTENT
        }
        val response : String = "Bugetul familiei insumeaza : " + budget + " lei.";
        return ResponseEntity(response, httpStatus)
    }

    @RequestMapping(value = ["/family/outgo"], method = [RequestMethod.GET])
    fun getOutgo() : ResponseEntity<String>{
        val outgo = accountantService.calculateOutGo()
        var httpStatus = HttpStatus.OK
        if(outgo == 0.0){
            httpStatus = HttpStatus.NO_CONTENT
        }
        val response : String = "Cheltuielile familiei insumeaza : " + outgo + " lei.";
        return ResponseEntity(response, httpStatus)
    }
}