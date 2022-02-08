package tech.garymyers.ecommerce.api.authentication.infrastructure

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification


import static io.micronaut.http.HttpRequest.POST
import static io.micronaut.http.HttpStatus.UNAUTHORIZED

@MicronautTest(transactional = false)
class AuthenticationControllerSpecification extends Specification {
   @Inject @Client("/api") HttpClient httpClient

   void "successful login" () {
      given:
      final user = new UsernamePasswordCredentials("Bob", "password")

      when:
      def response = httpClient.toBlocking().exchange(POST("/login", user), Argument.of(String), Argument.of(String))

      then:
      notThrown(Exception)
      def json = response.bodyAsJson()
      json.username == "Bob"
      json.roles == ["HELLO"]
      json.access_token != null
   }

   void "failed login" () {
      given:
      given:
      final user = new UsernamePasswordCredentials("Bob", 'p@$$w0rd')

      when:
      def response = httpClient.toBlocking().exchange(POST("/login", user), Argument.of(String), Argument.of(String))

      then:
      def ex = thrown(HttpClientResponseException)
      ex.status == UNAUTHORIZED
      ex.response.contentLength == -1
   }
}
