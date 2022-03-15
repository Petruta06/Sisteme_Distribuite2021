package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhoneAgenda {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<PhoneAgenda>(*args)
        }
    }
}