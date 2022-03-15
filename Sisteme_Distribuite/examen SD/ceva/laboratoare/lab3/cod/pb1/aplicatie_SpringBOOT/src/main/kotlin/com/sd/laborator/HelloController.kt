package com.sd.laborator

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
/*clasa controller in care instantiez un serviciu, creez o calea catre acesta
si il pun sa faca ceva anume
 */
class HelloController {
    val service:HelloService = HelloService()

    @RequestMapping(value=["/catel"], method = [RequestMethod.GET])
    @ResponseBody
    fun hello()= service.getHello()
}