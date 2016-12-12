package example.e2e

import example.Hello
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

/**
 * An example of API integration test.
 *
 * @author Hidetake Iwata
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class HelloControllerSpec extends Specification {
    @Autowired
    TestRestTemplate restTemplate

    def '/hello should return world'() {
        when:
        def entity = restTemplate.getForEntity('/hello', Hello)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.name == 'world'
    }
}