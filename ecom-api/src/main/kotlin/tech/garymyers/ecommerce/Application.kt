package tech.garymyers.ecommerce

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
   build()
      .args(*args)
      .packages("tech.garymyers.ecommerce")
      .start()
}
