package tech.garymyers.ecommerce.api.administrator.infrastructure

import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import tech.garymyers.ecommerce.api.administrator.AdministratorEntity
import java.util.UUID
import javax.transaction.Transactional

@JdbcRepository
interface AdministratorRepository: CoroutineCrudRepository<AdministratorEntity, UUID> {

   @Query(
      """
         INSERT INTO administrator(username, password)
         VALUES (:entity.username, hash_password(:entity.password))
      """,
      nativeQuery = true
   )
   @Transactional
   suspend fun saveWithHashed(entity: AdministratorEntity): AdministratorEntity

   @Query(
      """
         SELECT 
            admin.id AS id,
            admin.username AS username,
            admin.password AS password
         FROM administrator admin
         WHERE password_matches(:password, admin.password) 
      """,
      nativeQuery = true

   )
   suspend fun findUserByUsernameAndPassword(username: String, password: String): AdministratorEntity?
}