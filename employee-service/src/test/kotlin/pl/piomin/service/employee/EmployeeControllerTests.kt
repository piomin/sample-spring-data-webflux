package pl.piomin.service.employee;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import pl.piomin.service.employee.model.Employee

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(OrderAnnotation::class)
public class EmployeeControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    companion object {

        @Container
        @ServiceConnection
        val container = PostgreSQLContainer<Nothing>("postgres:14").apply {
            withDatabaseName("spring")
            withUsername("spring")
            withPassword("spring123")
        }

    }

    @Test
    @Order(1)
    fun shouldStart() {

    }

    @Test
    @Order(2)
    fun shouldAddEmployee() {
        webTestClient.post().uri("/employees").contentType(MediaType.APPLICATION_JSON)
            .bodyValue(Employee("Test", 1000, 1))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .jsonPath("$.id").isNotEmpty
    }

    @Test
    @Order(3)
    fun shouldFindEmployee() {
        webTestClient.get().uri("/employees/1").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .jsonPath("$.id").isNotEmpty
    }

    @Test
    @Order(3)
    fun shouldFindEmployees() {
        webTestClient.get().uri("/employees").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().jsonPath("$.length()").isEqualTo(1)
            .jsonPath("$[0].id").isNotEmpty
    }

}
