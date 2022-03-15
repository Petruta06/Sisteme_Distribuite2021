package com.sd.laborator.microservices

import com.sd.laborator.interfaces.CacheInterface
import com.sd.laborator.interfaces.LibraryDAO
import com.sd.laborator.interfaces.LibraryPrinter
import com.sd.laborator.services.CacheService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class LibraryPrinterMicroservice {
    @Autowired
    private lateinit var libraryDAO: LibraryDAO

    @Autowired
    private lateinit var libraryPrinter: LibraryPrinter

    @Autowired
    private lateinit var cacheService: CacheInterface

    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format",
        defaultValue = "") format: String): String {
        return when(format) {
            "html" -> libraryPrinter.printHTML(libraryDAO.getBooks())
            "json" -> libraryPrinter.printJSON(libraryDAO.getBooks())
            "raw" -> libraryPrinter.printRaw(libraryDAO.getBooks())
            else -> "Not implemented"
        }
    }
    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(@RequestParam(required = false, name = "author",
        defaultValue = "") author: String,
                   @RequestParam(required = false, name = "title",
                       defaultValue = "") title: String,
                   @RequestParam(required = false, name = "publisher",
                       defaultValue = "") publisher: String): String {
        if (author != "")
        {
            var lista = cacheService.findAllByAuthor(author)
            if(lista.isEmpty())
            {
                lista = this.libraryDAO.findAllByAuthor(author)
                cacheService.writeAll(lista)
                return this.libraryPrinter.printJSON(lista)
            }
            else
            {
                cacheService.updateAll(lista)
                return this.libraryPrinter.printJSON(lista)
            }

        }
        if (title != "")

        {
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(title))
        }
        if (publisher != "")

        {
            return this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(publisher))
        }

        return "Not a valid field"
    }
}