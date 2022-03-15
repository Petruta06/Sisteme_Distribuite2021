package org.examen.controllers

import org.apache.tomcat.util.http.ResponseUtil
import org.examen.interfaces.FamilyService
import org.examen.pojo.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Accountant{
    @Autowired
    private lateinit var familyService: FamilyService

    @RequestMapping(value = ["/family"], method = [RequestMethod.GET])
    fun getFamily() : List<Member>{
        return familyService.getFamily()
    }

    @RequestMapping(value = ["/member/{id}"], method = [RequestMethod.GET])
    fun getMember(@PathVariable id : Int) : ResponseEntity<Member?>{
        if(familyService.memberinFamily(id)){
            return ResponseEntity(familyService.getMember(id), HttpStatus.OK)
        }
        else {
            return ResponseEntity(familyService.getMember(id), HttpStatus.NOT_FOUND)
        }
    }

    @RequestMapping(value = ["/member"], method = [RequestMethod.POST])
    fun addMember(@RequestBody member: Member) : ResponseEntity<Unit>{
        if(familyService.memberinFamily(member.id)){
            return ResponseEntity(Unit, HttpStatus.NOT_ACCEPTABLE)
        }
        familyService.addMember(member)
        return ResponseEntity(Unit, HttpStatus.OK)
    }

    @RequestMapping(value = ["/member/{id}"], method = [RequestMethod.DELETE])
    fun killMember(@PathVariable id: Int) : ResponseEntity<Unit>{
        if(familyService.memberinFamily(id)){
            familyService.killMember(id)
            return ResponseEntity(Unit, HttpStatus.OK)
        }
        return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value = ["/venit"], method = [RequestMethod.PUT])
    fun addVenit(@RequestParam(required = true, name="id", defaultValue = "") id : String,
                 @RequestParam(required = true, name="venit", defaultValue = "") venit : String) : ResponseEntity<Unit>{
        if(familyService.memberinFamily(id.toInt())){
            familyService.addVenit(id.toInt(), venit.toDouble())
            return ResponseEntity(Unit,HttpStatus.ACCEPTED)
        }
        return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value = ["/cheltuiala"], method = [RequestMethod.PUT])
    fun addCheltuiala(@RequestParam(required = true, name="id", defaultValue = "") id : String,
                      @RequestParam(required = true, name="produs", defaultValue = "") produs : String,
                      @RequestParam(required = true, name="pret", defaultValue = "") pret : String) : ResponseEntity<Unit>{
        if(familyService.memberinFamily(id.toInt())){
            familyService.addCheltuiala(id.toInt(),Pair<String, Double>(produs, pret.toDouble()))
            return ResponseEntity(Unit,HttpStatus.ACCEPTED)
        }
        return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value = ["/venit/{id}"], method = [RequestMethod.GET])
    fun getVenit(@PathVariable id : Int) : ResponseEntity<Double>{
        if(familyService.memberinFamily(id)){
            val venit = familyService.getVenit(id)
            return ResponseEntity(venit,HttpStatus.OK)
        }
        return ResponseEntity(0.0, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value = ["/cheltuieli/{id}"], method = [RequestMethod.GET])
    fun getCheltuieli(@PathVariable id : Int) : ResponseEntity<Double>{
        if(familyService.memberinFamily(id)){
            val cheltuieli = familyService.getCheltuieli(id)
            return ResponseEntity(cheltuieli,HttpStatus.OK)
        }
        return ResponseEntity(0.0, HttpStatus.NOT_FOUND)
    }

    @RequestMapping(value = ["/family/cheltuieli"], method = [RequestMethod.GET])
    fun getCheltuieliTotal() : ResponseEntity<Double>{
        val cheltuieli = familyService.calculeazaCheltuieliTotaleFamilie()
        return ResponseEntity(cheltuieli,HttpStatus.OK)
    }

    @RequestMapping(value = ["/family/venit"], method = [RequestMethod.GET])
    fun getVenitTotal() : ResponseEntity<Double>{
        val venit = familyService.calculeazaVenitTotalFamilie()
        return ResponseEntity(venit,HttpStatus.OK)
    }
}