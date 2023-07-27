package pl.piomin.service.employee

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer

@TestConfiguration
class PostgresContainerDevMode {

    @Bean
    @ServiceConnection
    fun postgresql(): PostgreSQLContainer<*>? {
        return PostgreSQLContainer("postgres:14.0")
            .withUsername("spring")
            .withPassword("spring123")
    }
}