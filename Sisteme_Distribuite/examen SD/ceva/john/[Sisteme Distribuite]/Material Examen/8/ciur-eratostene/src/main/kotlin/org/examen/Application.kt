package org.examen

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("org.examen")
                .mainClass(Application.javaClass)
                .start()
    }
}