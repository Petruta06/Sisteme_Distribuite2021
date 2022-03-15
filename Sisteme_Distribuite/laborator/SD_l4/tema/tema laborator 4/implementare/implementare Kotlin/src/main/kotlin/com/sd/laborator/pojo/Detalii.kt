package com.sd.laborator.pojo

import java.util.*

/*clasa care retine datele despre cheltuielile familie*/
data class Detalii(
    var id:Int = 0,
    var persoana :String = "",
    var obiect : String = "",
    var pret : Double = 0.0,
    var ziuaSaptamanii:String = ""
)
