package tech.garymyers.ecommerce.extensions

import groovy.json.JsonSlurper
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import java.time.OffsetDateTime


import static io.micronaut.http.HttpStatus.NO_CONTENT

class HttpResponseExtension {
   static final jsonSlurper = new JsonSlurper()
   static final uuidRegex = ~"^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$"
   static final dateFormatRegex = ~"^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d+Z\$"

   static Object bodyAsJson(final HttpResponse self) throws HttpClientException {
      if (self.body() != null) {
         final json = jsonSlurper.parseText(self.body())
         final deque = new ArrayDeque()

         if(json instanceof Map && json.elements != null) {
            json.elements.each {
               deque.push(it)
            }
         } else {
            deque.push(json)
         }

         while(!deque.isEmpty()) {
            final element = deque.pop()

            if (element instanceof Map) {
               element.each { key, value ->
                  if ((key == "id" || key.toString().endsWith("Id")) && value instanceof String && uuidRegex.matcher(value).matches()) {
                     element[key] = UUID.fromString(value)
                  } else if (value instanceof String && dateFormatRegex.matcher(value).matches()) {
                     element[key] = OffsetDateTime.parse(value)
                  } else {
                     deque.push(value)
                  }
               }
            } else if (element instanceof Collection) {
               element.each {
                  deque.push(it)
               }
            }
         }

         return json
      } else if (self.status == NO_CONTENT) {
         throw new HttpClientResponseException(null, self)
      } else {
         null
      }
   }
}
