package tech.garymyers.ecommerce.api.administrator.infrastructure

import io.micronaut.core.convert.ConversionContext
import io.micronaut.data.model.runtime.convert.AttributeConverter
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.apache.commons.lang3.StringUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Singleton
class PasswordAttributeConverter @Inject constructor(
   private val bcryptPasswordEncoder: BCryptPasswordEncoder,
) : AttributeConverter<String, String> {

   override fun convertToPersistedValue(entityValue: String?, context: ConversionContext): String? {
      return bcryptPasswordEncoder.encode(entityValue)
   }

   override fun convertToEntityValue(persistedValue: String?, context: ConversionContext): String {
      return StringUtils.EMPTY
   }
}