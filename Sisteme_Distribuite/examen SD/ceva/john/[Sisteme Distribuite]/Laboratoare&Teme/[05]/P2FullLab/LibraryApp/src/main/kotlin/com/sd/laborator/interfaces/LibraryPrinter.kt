package com.sd.laborator.interfaces

import com.sd.laborator.model.Book

interface LibraryPrinter{
        fun print(books : Set<Book>, format : String) : String
}