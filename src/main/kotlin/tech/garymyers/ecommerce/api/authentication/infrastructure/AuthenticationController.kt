package tech.garymyers.ecommerce.api.authentication.infrastructure

import io.micronaut.context.annotation.Replaces
import io.micronaut.context.event.ApplicationEvent
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.endpoints.LoginController
import io.micronaut.security.handlers.LoginHandler
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS
import jakarta.inject.Inject
import tech.garymyers.ecommerce.api.authentication.AuthenticationService
import tech.garymyers.ecommerce.api.authentication.FailedAuthenticationStatus
import tech.garymyers.ecommerce.api.authentication.SuccessAuthenticationStatus
import javax.validation.Valid

@Controller("/api/login")
@Secured(IS_ANONYMOUS)
@Replaces(LoginController::class)
class AuthenticationController @Inject constructor(
   private val eventPublisher: ApplicationEventPublisher<ApplicationEvent>,
   private val authenticationService: AuthenticationService,
   private val loginHandler: LoginHandler,
) {

   @Post(processes = [MediaType.APPLICATION_JSON])
   suspend fun login(
      @Valid @Body usernamePasswordCredentials: UsernamePasswordCredentials
   ): HttpResponse<*> {
      return when(authenticationService.authenticateMe(usernamePasswordCredentials)) {
         is SuccessAuthenticationStatus -> {
            HttpResponse.ok<Any>()
         }

         is FailedAuthenticationStatus -> {
            HttpResponse.status<Any>(HttpStatus.UNAUTHORIZED)
         }
      }
   }
}
