package tech.garymyers.ecommerce.helloworld.infrastructure


import io.micronaut.test.extensions.spock.annotation.MicronautTest
import tech.garymyers.ecommerce.spi.ControllerSpecificationBase

@MicronautTest(transactional = false)
class HelloWorldControllerSpecification extends ControllerSpecificationBase {
   void "get hello world" () {
      when:
      def json = get("/hello/world")

      then:
      notThrown(Exception)

      json.message == "Hello World"
   }
}
