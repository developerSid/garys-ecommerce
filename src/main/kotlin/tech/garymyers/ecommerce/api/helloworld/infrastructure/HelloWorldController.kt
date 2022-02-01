package tech.garymyers.ecommerce.api.helloworld.infrastructure

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED

@Secured(IS_AUTHENTICATED)
@Controller("/api/hello/world")
class HelloWorldController {

   @Get(produces = [MediaType.APPLICATION_JSON])
   @Secured("HELLO")
   fun helloWorld(): Map<String, String> {
      return mapOf("message" to "Hello World")
   }
}