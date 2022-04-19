package tech.garymyers.ecommerce.api.administrator

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.util.UUID

@Introspected
@MappedEntity("administrator")
data class AdministratorEntity(

   @field:Id
   @field:GeneratedValue
   var id: UUID? = null,

   val username: String,

   val password: String,
)