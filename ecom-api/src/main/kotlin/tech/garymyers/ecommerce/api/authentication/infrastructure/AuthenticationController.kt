package tech.garymyers.ecommerce.api.authentication.infrastructure

import io.micronaut.context.annotation.Replaces
import io.micronaut.context.event.ApplicationEvent
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.endpoints.LoginController
import io.micronaut.security.event.LoginFailedEvent
import io.micronaut.security.event.LoginSuccessfulEvent
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
      @Valid @Body usernamePasswordCredentials: UsernamePasswordCredentials,
      request: HttpRequest<*>,
   ): HttpResponse<*> {
      return when(val authentication = authenticationService.authenticateMe(usernamePasswordCredentials)) {
         is SuccessAuthenticationStatus -> {
            eventPublisher.publishEvent(LoginSuccessfulEvent(authentication.authentication.name))

            loginHandler.loginSuccess(authentication.authentication, request)
         }

         is FailedAuthenticationStatus -> {
            eventPublisher.publishEvent(LoginFailedEvent(authentication.reason))

            HttpResponse.status<Any>(HttpStatus.UNAUTHORIZED)
         }
      }
   }
}
