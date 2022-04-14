package tech.garymyers.ecommerce.api.authentication

import io.micronaut.data.exceptions.EmptyResultException
import io.micronaut.security.authentication.ServerAuthentication
import io.micronaut.security.authentication.UsernamePasswordCredentials
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.garymyers.ecommerce.api.administrator.infrastructure.AdministratorRepository

@Singleton
class AuthenticationService @Inject constructor(
   private val administratorRepository: AdministratorRepository,
) {
   private val logger = LoggerFactory.getLogger(AuthenticationService::class.java)

   suspend fun authenticateAdmin(usernamePasswordCredentials: UsernamePasswordCredentials): AuthenticationStatus {
      return try {
         val admin = administratorRepository.findUserByUsernameAndPassword(usernamePasswordCredentials.username, usernamePasswordCredentials.password)

         SuccessAuthenticationStatus(
            ServerAuthentication(
               admin!!.username,
               listOf("ADMIN"),  // TODO load user's roles from `admin` variable
               emptyMap(),
            )
         )
      } catch (e: EmptyResultException) {
         logger.warn("Username/password does not match for {}", usernamePasswordCredentials.username)

         FailedAuthenticationStatus("Username/password does not match") // TODO localize this message
      }
   }
}