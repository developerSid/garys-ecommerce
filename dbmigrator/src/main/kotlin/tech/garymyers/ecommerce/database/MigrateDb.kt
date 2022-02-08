package tech.garymyers.ecommerce.database

import org.flywaydb.core.Flyway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.CommandLine
import picocli.CommandLine.Option
import java.util.concurrent.Callable
import kotlin.system.exitProcess

private val logger: Logger = LoggerFactory.getLogger("Migration")

class MigrateDb: Callable<Int> {

   @Option(names = ["-u", "--user"], description = ["db username"])
   lateinit var user: String

   @Option(names = ["-p", "--password"], description = ["db password"])
   lateinit var password: String

   @Option(names = ["-P", "--port"], defaultValue = "5432", description = ["db port"])
   var port: Int = 5432

   @Option(names = ["-d", "--database"], description = ["database to migrate"])
   lateinit var database: String

   @Option(names = ["-H", "--host"], description = ["host database is running on"])
   lateinit var host: String

   @Option(names = ["-c", "--force-clean"], defaultValue = "false", description = ["Reset the database back to zero and apply all migrations"])
   var forceClean: Boolean = false

   override fun call(): Int {
      return try {
         val flyway = Flyway.configure()
            .locations("classpath:db/migration/postgres")
            .cleanDisabled(!forceClean)
            .cleanOnValidationError(forceClean)
            .table("flyway_schema_history")
            .initSql("SELECT 1")
            .dataSource("jdbc:postgresql://$host:$port/$database", user, password)
            .load()

         if (forceClean) {
            logger.info("Cleaning database {}", database)
            flyway.clean()
         }

         logger.info("Migration database {}", database)
         flyway.migrate()

         0
      } catch (e: Throwable) {
         logger.error("Error occurred. Unable to migrate database", e)
         1
      }
   }
}

fun main(args: Array<String>): Unit =
   exitProcess(CommandLine(MigrateDb()).execute(*args))