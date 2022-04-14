package tech.garymyers.ecommerce.api.administrator

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.TypeDef
import io.micronaut.data.model.DataType
import tech.garymyers.ecommerce.api.administrator.infrastructure.PasswordAttributeConverter
import java.util.UUID

@Introspected
@MappedEntity("administrator")
data class AdministratorEntity(

   @field:Id
   @field:GeneratedValue
   var id: UUID? = null,

   val username: String,

   @field:TypeDef(type = DataType.STRING, converter = PasswordAttributeConverter::class)
   val password: String,
)