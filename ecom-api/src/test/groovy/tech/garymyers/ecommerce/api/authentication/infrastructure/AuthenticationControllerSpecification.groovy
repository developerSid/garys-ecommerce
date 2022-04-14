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
import tech.garymyers.ecommerce.api.administrator.AdministratorTestDataLoaderService


import static io.micronaut.http.HttpRequest.POST
import static io.micronaut.http.HttpStatus.UNAUTHORIZED

@MicronautTest(transactional = false)
class AuthenticationControllerSpecification extends Specification {
   @Inject @Client("/api") HttpClient httpClient
   @Inject AdministratorTestDataLoaderService administratorTestDataLoaderService

   void "successful login" () {
      given:
      final user = administratorTestDataLoaderService.stream(1).findFirst().orElseThrow()
      final loginPayload = new UsernamePasswordCredentials(user.username, user.password)

      when:
      def response = httpClient.toBlocking().exchange(POST("/login/admin", loginPayload), Argument.of(String), Argument.of(String))

      then:
      notThrown(Exception)
      def json = response.bodyAsJson()
      json.username == user.username
      json.roles == ["ADMIN"]
      json.access_token != null
   }

   void "failed login" () {
      given:
      final user = new UsernamePasswordCredentials("Bob", 'p@$$w0rd')

      when:
      def response = httpClient.toBlocking().exchange(POST("/login/admin", user), Argument.of(String), Argument.of(String))

      then:
      def ex = thrown(HttpClientResponseException)
      ex.status == UNAUTHORIZED
      ex.response.contentLength == -1
   }
}
