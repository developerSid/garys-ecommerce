package tech.garymyers.ecommerce.api.administrator.infrastructure

import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import tech.garymyers.ecommerce.api.administrator.AdministratorEntity
import java.util.UUID

@JdbcRepository
interface AdministratorRepository : CoroutineCrudRepository<AdministratorEntity, UUID> {

   @Query(
      """
         SELECT 
            admin.id AS id,
            admin.username AS username,
            admin.password AS password
         FROM administrator admin
         WHERE password_matches(:password, admin.password)
               AND admin.username = :username
      """,
      nativeQuery = true

   )
   suspend fun findUserByUsernameAndPassword(username: String, password: String): AdministratorEntity?
}