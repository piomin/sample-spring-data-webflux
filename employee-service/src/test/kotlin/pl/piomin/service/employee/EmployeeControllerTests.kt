package pl.piomin.service.employee;

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class EmployeeControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    companion object {

        @Container
        val container = PostgreSQLContainer<Nothing>("postgres:14").apply {
            withDatabaseName("spring")
            withUsername("spring")
            withPassword("spring123")
        }

    }

    @Test
    fun shouldStart() {

    }

}
