package com.sd.laborator.services

import com.sd.laborator.interfaces.CacheInterface
import com.sd.laborator.pojo.Person
import org.springframework.stereotype.Service
import java.io.File
import java.math.BigInteger
import java.sql.Timestamp

@Service
class CacheService: CacheInterface {
    private val f = File("/home/ana/Desktop/examen SD/subiecte/cache_agenda/cache.txt")



    override fun write(list: List<Person>) {
        for(p in list)
        {
            //extrag mai intai timpul
            val timestamp:Timestamp = Timestamp(System.currentTimeMillis())
            //construiesc sirul de scris in fisier
            var time =2
            time = (time/1000)/60
            val s = "Ora " + time.toString() +
                    " id " + p.id +
                    " lastName "+ p.lastName+
                    " firstName "+ p.firstName +
                    " telephone "+ p.telephoneNumber+
                    "\n"
            //scriu in fisier

          if(!f.exists())
            {
                f.createNewFile()

            }
            f.appendText(s)
        }

    }


    override fun isValid(s: String):Boolean {
        var nr = s.toInt()

        val timestamp:Timestamp = Timestamp(System.currentTimeMillis())
        var time = Integer.parseInt(timestamp.time.toString())
        time = (time/1000)/60
        var diff = time - nr
        if(diff<30)
        {
            return true
        }
        return false


    }

    override fun read():String {
        var list:String =""
      if(f.exists())
      {
         list = f.readText()
      }
        return list
    }

    override fun search(s: String): List<Person> {
       val content = read()
        var result:List<Person> = listOf()
        if(content!="")
        {
            val persons:List<String>
            persons = content.split('\n')
            for(p in persons)
            {
                //var (t, timp, i, id, l, lastName, f, firstName, te,tele) = p.split(" ", 10)
                var c = p.split(" ")
                if((c.size==10) && (c[5]==s || c[7]==s) && isValid(c[1]))
                {

                    var pers = Person(c[3].toInt(), c[5], c[7], c[9])
                    update(pers)
                    result.plus(pers)
                }
                var debug = File("/home/ana/Desktop/examen SD/subiecte/cache_agenda/debug.txt")
                if(!debug.exists())
                    debug.createNewFile()
                debug.appendText(c.toString()+"\t" + p.toString()+"\n")
            }
        }

        return result

    }

    override fun update(s:Person) {
        val content = read()
        f.writeText("")
        if(content!="")
        {
            val persons:List<String>
            persons = content.split('\n')
            for(p in persons)
            {
                //var (t, timp, i, id, l, lastName, f, firstName, te,tele) = p.split(" ", 10)
                var c = p.split(" ")
                if((c[5]==s.firstName || c[7]==s.lastName))
                {
                    var l =listOf<Person>()
                    l.plus(s)
                    write(l)
                }
                else
                {
                    f.appendText(p+"\n")
                }

            }
        }
    }
}