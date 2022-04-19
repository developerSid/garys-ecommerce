package tech.garymyers.ecommerce.api.administrator

import com.github.javafaker.Faker
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import tech.garymyers.ecommerce.api.administrator.infrastructure.AdministratorRepository
import java.util.stream.IntStream
import java.util.stream.Stream
import javax.transaction.Transactional
import kotlin.streams.asSequence

object AdministratorTestDataLoader {

   @JvmStatic
   fun stream(numberIn: Int = 1): Stream<AdministratorEntity> {
      val number = if (numberIn > 0) numberIn else 1
      val faker = Faker()
      val userFaker = faker.name()

      return IntStream.range(0, number).mapToObj {
         AdministratorEntity(
            id = null,
            username = userFaker.username(),
            password = "password",
         )
      }
   }
}

@Singleton
open class AdministratorTestDataLoaderService @Inject constructor(
   private val administratorRepository: AdministratorRepository
) {
   @Transactional
   open fun stream(numberIn: Int = 1): Stream<AdministratorEntity> {
      return runBlocking {
         val toReturn = mutableListOf<AdministratorEntity>()

         AdministratorTestDataLoader.stream(numberIn).asSequence().forEach {
            toReturn.add(administratorRepository.saveAdmin(it).copy(password = it.password))
         }

         toReturn.stream()
      }
   }
}