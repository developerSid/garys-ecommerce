package tech.garymyers.ecommerce.api.administrator.infrastructure

import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.lang3.StringUtils
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.statement.StatementContext
import tech.garymyers.ecommerce.api.administrator.AdministratorEntity
import java.sql.ResultSet
import java.util.UUID
import javax.transaction.Transactional

@JdbcRepository
abstract class AdministratorRepository @Inject constructor(
   private val jdbi: Jdbi,
) : CoroutineCrudRepository<AdministratorEntity, UUID> {

   override suspend fun <S : AdministratorEntity> save(entity: S): S {
      TODO("Make me throw an exception or call saveAdmin somehow")
   }

   @Transactional
   fun saveAdmin(entity: AdministratorEntity): AdministratorEntity {
      return jdbi.withHandle<AdministratorEntity, Exception> { handle ->
         val query = handle.createQuery("""
            INSERT INTO administrator (username, password)
            VALUES (:username, hash_password(:password))
            RETURNING 
               *
         """.trimIndent())

         query.bind("username", entity.username)
         query.bind("password", entity.password)

         query.map { rs: ResultSet, ctx: StatementContext ->
            AdministratorEntity(
               id = rs.getObject("id", UUID::class.java),
               username = rs.getString("username"),
               password = StringUtils.EMPTY
            )
         }.findFirst().orElseThrow() // FIXME setup EmptyResultException
      }
   }

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
   abstract suspend fun findUserByUsernameAndPassword(username: String, password: String): AdministratorEntity?
}