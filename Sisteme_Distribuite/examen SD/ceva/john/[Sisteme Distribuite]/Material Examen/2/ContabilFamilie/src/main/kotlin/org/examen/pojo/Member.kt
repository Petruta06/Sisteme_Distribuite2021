package org.examen.pojo

import java.util.concurrent.ConcurrentHashMap

data class Member(
    var id : Int = 0,
    var nume : String = "",
    var venit : Double = 0.0,
    var cheltuieli : Double = 0.0,
    var listaCheltuieli : ConcurrentHashMap<String, Double> = ConcurrentHashMap<String, Double>()
)