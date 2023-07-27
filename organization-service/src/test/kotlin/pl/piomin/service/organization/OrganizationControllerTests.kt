package pl.piomin.service.organization;

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
import pl.piomin.service.organization.model.Organization

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(OrderAnnotation::class)
public class OrganizationControllerTests {

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
    fun shouldAddOrganization() {
        webTestClient.post().uri("/organizations").contentType(MediaType.APPLICATION_JSON)
            .bodyValue(Organization("Test"))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .jsonPath("$.id").isNotEmpty
    }

    @Test
    @Order(3)
    fun shouldFindOrganization() {
        webTestClient.get().uri("/organizations/1").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
    }

    @Test
    @Order(3)
    fun shouldFindOrganizations() {
        webTestClient.get().uri("/organizations").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().jsonPath("$.length()").isEqualTo(1)
            .jsonPath("$[0].id").isNotEmpty
    }
}
