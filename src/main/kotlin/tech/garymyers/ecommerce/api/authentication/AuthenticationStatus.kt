package tech.garymyers.ecommerce.api.authentication

import io.micronaut.security.authentication.Authentication

sealed interface AuthenticationStatus

data class SuccessAuthenticationStatus(
   val authentication: Authentication,
) : AuthenticationStatus

class FailedAuthenticationStatus(
   val reason: String,
) : AuthenticationStatus
