package com.cogrammer.testcontainers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration

import static org.assertj.core.api.Assertions.assertThat

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
class ApplicationSpec extends ApplicationSpecContext {

    @Autowired
    private TestRestTemplate restTemplate

    def "when GET /api/v1/products is called it returns status code 200"() {
        expect: "Status code is 200"
        when:
            def response = restTemplate.getForEntity("/api/v1/products", Object.class)
        then:
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    def "when GET /api/v1/greetings with name query parameter is called it returns status code 200"() {
        expect: "Status code is 200"
        when:
            def response = restTemplate.getForEntity("/api/v1/greetings?name=Tom", Object.class)
        then:
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }
}
