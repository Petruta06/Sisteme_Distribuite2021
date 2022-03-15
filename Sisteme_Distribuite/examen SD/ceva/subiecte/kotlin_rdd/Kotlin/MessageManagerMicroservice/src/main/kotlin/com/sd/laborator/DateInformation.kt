package com.sd.laborator

import khttp.get

class DateInformation {

    fun getInformation(url:String):String{
        //fac cererea
        val response = get(url)
        return response.text

    }
}