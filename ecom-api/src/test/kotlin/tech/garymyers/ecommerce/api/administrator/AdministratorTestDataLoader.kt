package tech.garymyers.ecommerce.api.administrator

import com.github.javafaker.Faker
import jakarta.inject.Inject
import jakarta.inject.Singleton
import tech.garymyers.ecommerce.api.administrator.infrastructure.AdministratorRepository
import java.util.stream.IntStream
import java.util.stream.Stream

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
class AdministratorTestDataLoaderService @Inject constructor(
   private val administratorRepository: AdministratorRepository
) {
   fun stream(numberIn: Int = 1): Stream<AdministratorEntity> {
      TODO("Finish me on Tuesday")
//      return AdministratorTestDataLoader.stream(numberIn)
//         .map { administratorRepository.saveWithHashed(it) }
   }
}