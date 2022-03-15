package org.examen.microservices

import org.examen.interfaces.LibraryDAO
import org.examen.interfaces.LibraryPrinter
import org.examen.model.Book
import org.examen.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class LibraryPrinterMicroservice{
    @Autowired
    private lateinit var libraryDAO: LibraryDAO

    @Autowired
    private lateinit var libraryPrinter: LibraryPrinter

    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required =  true, name = "format", defaultValue = "") format : String) : String{
        return when(format){
            "html" -> libraryPrinter.printHTML(libraryDAO.getBooks())
            "json" -> libraryPrinter.printJSON(libraryDAO.getBooks())
            "raw" -> libraryPrinter.printRaw(libraryDAO.getBooks())
            else -> "Not implemented!"
        }
    }
    @RequestMapping("/addbook", method = [RequestMethod.GET])
    @ResponseBody
    fun addBook(@RequestParam(required = true, name="author", defaultValue = "") author : String,
                   @RequestParam(required = true, name="title", defaultValue = "") title : String,
                   @RequestParam(required = true, name="publisher", defaultValue = "") publisher : String,
                   @RequestParam(required = true, name="content", defaultValue = "") content : String) : String {
        if(author == ""){
            return "<h1 style=\"text-align:center;\">Cartea nu are autor?</h1>"
        }
        if(title == ""){
            return "<h1 style=\"text-align:center;\">Cartea nu are titlu?</h1>"
        }
        if(publisher == ""){
            return "<h1 style=\"text-align:center;\">Cartea nu are publisher?</h1>"
        }
        if(content == ""){
            return "<h1 style=\"text-align:center;\">Cartea nu are continut?</h1>"
        }
        libraryDAO.addBook(Book(Content(author,content,title,publisher)))
        return "<h1 style=\"text-align:center;\">Cartea a fost adaugata cu succes</h1>"
    }

    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(@RequestParam(required = false, name="author", defaultValue = "") author : String,
                   @RequestParam(required = false, name="title", defaultValue = "") title : String,
                   @RequestParam(required = false, name="publisher", defaultValue = "") publisher : String) : String{
        if(author != ""){
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByAuthor(author))
        }
        if(title != ""){
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(title))
        }
        if(publisher != ""){
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(publisher))
        }
        return "Not a valid field!"
    }

    @RequestMapping("/find/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customFindandPrint(@RequestParam(required = false, name="author", defaultValue = "") author : String,
                   @RequestParam(required = false, name="title", defaultValue = "") title : String,
                   @RequestParam(required = false, name="publisher", defaultValue = "") publisher : String,
                   @RequestParam(required = false, name="format", defaultValue = "html") format : String) : String{
        if(author != ""){
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByAuthor(author))
        }
        if(title != ""){
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(title))
        }
        if(publisher != ""){
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(publisher))
        }
        return "Not a valid field!"
    }

    @RequestMapping("/", method = [RequestMethod.GET])
    @ResponseBody
    fun welcomeToThePage() : String{
        libraryDAO.createBookTable()
        return "<h1 style=\"text-align:center;\">Bun venit in librarie!</h1>"
    }
}