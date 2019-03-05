package com.cogrammer.testcontainers.service;

import com.cogrammer.testcontainers.config.RestTemplateConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
    GreetingService.class,
    RestTemplateConfiguration.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(initializers = GreetingServiceTest.Initializer.class)
@Testcontainers
class GreetingServiceTest {

    @Container
    private static final GenericContainer webserviceContainer = new GenericContainer("vad1mo/hello-world-rest")
            .withExposedPorts(5050)
            .waitingFor(new HttpWaitStrategy().forStatusCode(200));

    @Autowired
    private GreetingService service;

    @Test
    void should_greet_with_given_name() {
        // given

        // when
        final Map<String, String> result = service.greet("Tom");

        // then
        assertThat(result).containsOnlyKeys("message");
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            final String ipAddress = webserviceContainer.getContainerIpAddress();
            final Integer port = webserviceContainer.getMappedPort(5050);
            TestPropertyValues.of(
                "application.greeter-service.url=" + String.format("http://%s:%s/", ipAddress, port)
            ).applyTo(context);
        }
    }
}
