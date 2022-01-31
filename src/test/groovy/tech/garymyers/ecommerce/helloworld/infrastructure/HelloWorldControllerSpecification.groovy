package tech.garymyers.ecommerce.helloworld.infrastructure

import groovy.json.JsonSlurper
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(transactional = false)
class HelloWorldControllerSpecification extends Specification {
   @Inject @Client("/api/hello") HttpClient client

   void "get hello world" () {
      given:
      final jsonSlurper = new JsonSlurper()

      when:
      def response = client.toBlocking().exchange(HttpRequest.GET("/world"), Argument.of(String), Argument.of(String))

      then:
      notThrown(Exception)
      final body = response.body()
      final json = jsonSlurper.parseText(body)

      json.message == "Hello World"
   }
}
