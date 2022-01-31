package tech.garymyers.ecommerce.api.helloworld.infrastructure

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/api/hello/world")
class HelloWorldController {

   @Get
   fun helloWorld(): Map<String, String> {
      return mapOf("message" to "Hello World")
   }
}