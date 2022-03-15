package com.sd.laborator.services

import com.sd.laborator.interfaces.CacheInterface
import com.sd.laborator.model.Book
import com.sd.laborator.model.Content
import org.springframework.stereotype.Service
import java.io.File
import java.lang.Math.abs
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Service
class CacheService:CacheInterface {
    companion object
    {
        val fileName ="ana.txt"
        val myfile = File(fileName)
    }
    override fun writeInformation(book: Book) {
        var h = getHour()
        if(!myfile.exists())
        {
            myfile.createNewFile()
        }
        myfile.appendText(
            "ora: "+ h+
                    "\nTitlu: "+ book.name+"\n" +
                    "Autor: "+ book.author+
                    "\nContinut: "+ book.content+
                    "\nEditura: "+ book.publisher+"\n"
                    +"*****************") //delimitator

    }

    override fun writeAll(books: Set<Book>) {
        for(b in books)
        {
            this.writeInformation(b)
        }
    }

    override fun updateAll(books: Set<Book>) {
        for(b in books)
        {
            this.updateInformation(b)
        }
    }
    override fun findAllByAuthor(author: String): Set<Book> {
        var result:Set<Book> = mutableSetOf()
        if(!myfile.exists())
        {
            //myfile.createNewFile()
            return result
        }
        var continut =  myfile.inputStream().readBytes().toString(Charsets.UTF_8)
        var lista:List<String> = continut.split("*****************")

        for(c in lista)
        {
            var inf = c.split("\n")
            result.plus(Book(
                Content(c, c, c, c)))
            /*if(inf.size==1)
            {
                break
            }
            var autor =inf[1].split(" ")
            if(autor[1]==author && isValid(inf[0]))
            {
                result.plus(Book(
                    Content(inf[1].split(" ")[1],
                            inf[2].split(" ")[1],
                    inf[3].split(" ")[1],
                    inf[4].split(" ")[1]
                )))
            }*/
        }
        return result

    }

    override fun updateInformation(book: Book) {
        this.deleteInformation(book)
        this.writeInformation(book)
    }

    fun getHour():String
    {
        var currentDateTime= LocalDateTime.now()

        var time= currentDateTime.format(DateTimeFormatter.
        ofLocalizedTime(FormatStyle.SHORT)).removeSuffix(" AM").
        removeSuffix(" PM")
        return time
    }
    fun isValid(s:String):Boolean
    {
        var (sir, ora) = s.split(" ")
        var k = ora.split(":")
        var o1 = k[0].toInt()*60 + k[1].toInt()

        var currentDateTime= LocalDateTime.now()
        var time= currentDateTime.format(DateTimeFormatter.
        ofLocalizedTime(FormatStyle.SHORT)).removeSuffix(" AM").
        removeSuffix(" PM")
         k = time.split(":")
        var o2 = k[0].toInt()*60 + k[1].toInt()
      if(abs( o1 - o2) < 2)
      {
          return true // inf este valisa si poate fi luata din cache
      }
        return false
    }

    override fun deleteInformation(book: Book) {
        var continut = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
        var lista:List<String> = continut.split("*****************")
        //caut cartea si modific informatiile
        var i = -1;
        // de modificat chestiile astea
        for(c in lista)
        {
            i = i + 1
            val words = c.split("\n")
            if(words[1].split(" ")[1]==book.name &&
                words[2].split(" ")[1]==book.author &&
                words[3].split(" ")[1]==book.content &&
                words[4].split(" ")[1]==book.publisher)
            {
                lista.drop(i)
                break
            }
        }
    }
}