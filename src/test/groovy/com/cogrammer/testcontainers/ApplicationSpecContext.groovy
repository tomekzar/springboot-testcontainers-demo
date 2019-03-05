package com.cogrammer.testcontainers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class ApplicationSpecContext extends Specification {

    private static PostgreSQLContainer staticPostgreSQLContainer = new PostgreSQLContainer()
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("product-catalog")

    private static GenericContainer staticWsContainer = new GenericContainer("vad1mo/hello-world-rest")
            .withExposedPorts(5050)
            .waitingFor(new HttpWaitStrategy().forStatusCode(200))

    private static GenericContainer staticRedisContainer = new GenericContainer("redis:5.0.3-alpine")
            .withExposedPorts(6379)

    @Shared
    @SuppressWarnings("unused")
    PostgreSQLContainer postgreSQLContainer = staticPostgreSQLContainer

    @Shared
    @SuppressWarnings("unused")
    GenericContainer wsContainer = staticWsContainer

    @Shared
    @SuppressWarnings("unused")
    GenericContainer redisContainer = staticRedisContainer

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + staticPostgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + staticPostgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + staticPostgreSQLContainer.getPassword(),
                    "application.greeter-service.url=" + String.format("http://%s:%s/", staticWsContainer.getContainerIpAddress(), staticWsContainer.getMappedPort(5050)),
                    "spring.redis.host=" + staticRedisContainer.getContainerIpAddress(),
                    "spring.redis.port=" + staticRedisContainer.getMappedPort(6379)
            ).applyTo(context)
        }
    }
}
