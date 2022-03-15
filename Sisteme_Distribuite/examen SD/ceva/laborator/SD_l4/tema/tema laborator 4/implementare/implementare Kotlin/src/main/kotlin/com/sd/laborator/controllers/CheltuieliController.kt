package com.sd.laborator.controllers

import com.sd.laborator.pojo.Detalii
import com.sd.laborator.services.CheltuieliServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class CheltuieliController {
    @Autowired
    private lateinit var agendaCheltuieli: CheltuieliServices

    @RequestMapping(
        value = ["/detalii"], method =
        [RequestMethod.POST]
    )
    fun createDetalii(@RequestBody detaliu: Detalii): ResponseEntity<Unit> {
        agendaCheltuieli.createDetalii(detaliu)
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }

    @RequestMapping(
        value = ["/detaliu/{id}"], method =
        [RequestMethod.GET]
    )
    fun getDetaliu(@PathVariable id: Int): ResponseEntity<Detalii?> {
        val detaliuCautat: Detalii? = agendaCheltuieli.getDetalii(id)
        if (detaliuCautat != null) {
            return ResponseEntity(detaliuCautat, HttpStatus.OK)
        } else {
            return ResponseEntity(detaliuCautat, HttpStatus.NOT_FOUND)
        }
    }


       @RequestMapping (value = ["/detaliu_update/{id}"], method =
        [RequestMethod.PUT])
        fun updateDetaliu(@PathVariable id:Int,
                          @RequestBody detaliu:Detalii):ResponseEntity<Unit> {
           if (agendaCheltuieli.getDetalii(id) != null) {
               agendaCheltuieli.updateDetalii(id, detaliu)
               return ResponseEntity(Unit, HttpStatus.ACCEPTED)

           } else {
               return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
           }
       }

            @RequestMapping (value=["detaliu/{id}"], method = [ RequestMethod.DELETE])
        fun deleteDetaliu(@PathVariable id:Int):ResponseEntity<Unit>
        {
             var  status =HttpStatus.NOT_FOUND
            if(agendaCheltuieli.getDetalii(id)!=null)
            {
                agendaCheltuieli.deleteDetalii(id)
                status = HttpStatus.OK
            }
            return ResponseEntity(Unit,status )
        }

        @RequestMapping(value = ["/cautare"], method = [RequestMethod.GET])
        fun cautare(@RequestParam(required = false, name = "persoana", defaultValue = "") persoana:String,
            @RequestParam(required = false, name ="obiect", defaultValue = "") obiect:String,
            @RequestParam(required = false, name = "pret", defaultValue =  "0.0") pret:Double):
                ResponseEntity<List<Detalii>>
        {
            val detalii = agendaCheltuieli.searchDetalii(persoana,obiect, pret)
            var status = HttpStatus.OK
            if(detalii.isEmpty())
            {
                status = HttpStatus.NOT_FOUND
            }
            return ResponseEntity(detalii, status)
        }

    }

