package tech.garymyers.ecommerce

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Factory
class ExternalBeanFactory {

   @Bean
   fun bcryptPasswordEncoder() = BCryptPasswordEncoder(10)
}