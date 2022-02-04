package tech.garymyers.ecommerce.spi

import io.micronaut.core.type.Argument
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import jakarta.inject.Inject
import spock.lang.Specification
import tech.garymyers.ecommerce.extensions.HttpResponseExtension


import static io.micronaut.http.HttpRequest.GET
import static io.micronaut.http.HttpRequest.POST

class ControllerSpecificationBase extends Specification {
   @Inject @Client("/api") HttpClient httpClient

   BlockingHttpClient client
   String bobsAccessToken

   void setup() {
      this.client = httpClient.toBlocking()

      final authResponse = client.exchange(POST("/login", new UsernamePasswordCredentials("Bob", "password")), Argument.of(String), Argument.of(String))
      this.bobsAccessToken = authResponse.bodyAsJson().access_token
   }

   Object get(String path, String accessToken = bobsAccessToken) throws HttpResponseExtension {
      return client.exchange(
         GET(path).header("Authorization", "Bearer $accessToken"),
         Argument.of(String),
         Argument.of(String)
      ).bodyAsJson()
   }
}
