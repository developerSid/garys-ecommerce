package tech.garymyers.ecommerce.api.authentication

import io.micronaut.security.authentication.ServerAuthentication
import io.micronaut.security.authentication.UsernamePasswordCredentials
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AuthenticationService {

   suspend fun authenticateMe(usernamePasswordCredentials: UsernamePasswordCredentials): AuthenticationStatus {
      return when(usernamePasswordCredentials.username) {
         "Bob" -> {
            if (usernamePasswordCredentials.password == "password") {
               SuccessAuthenticationStatus(
                  ServerAuthentication(
                     usernamePasswordCredentials.username,
                     listOf("ROLE_HELLO"),
                     emptyMap(),
                  )
               )
            } else {
               FailedAuthenticationStatus("Bob's password did not match")
            }
         }

         else -> FailedAuthenticationStatus("You are not Bob")
      }
   }
}