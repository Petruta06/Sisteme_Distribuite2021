package com.sd.laborator.services

import com.sd.laborator.interfaces.*
import com.sd.laborator.model.Book
import org.springframework.stereotype.Service
import javax.sql.rowset.spi.XmlReader

@Service
class LibraryPrinterService: LibraryPrinter {
    override fun print(books: Set<Book>, format: String) : String {
        return when(format) {
            "html" -> HTMLPrinter.printer(books);
            "json" -> JSONPrinter.printer(books);
            "raw"  -> RawPrinter.printer(books);
            "xml"  -> XMLPrinter.printer(books);
            else -> "Not implemented";
        }
    }
}