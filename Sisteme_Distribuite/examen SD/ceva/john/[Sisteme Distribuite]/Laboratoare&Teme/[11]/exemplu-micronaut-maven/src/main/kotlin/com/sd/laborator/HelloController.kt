package com.sd.laborator
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Produces
@Controller("/hello")
class HelloController {
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/")
    fun hello(): String {
        return "Hello from Micronaut!\n" }
}