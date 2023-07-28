package pl.piomin.service.organization;

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import pl.piomin.service.organization.model.Employee
import pl.piomin.service.organization.model.Organization

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(OrderAnnotation::class)
public class OrganizationControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient
    private val serializer: ObjectMapper = ObjectMapper()

    companion object {

        @Container
        @ServiceConnection
        val container = PostgreSQLContainer<Nothing>("postgres:14").apply {
            withDatabaseName("spring")
            withUsername("spring")
            withPassword("spring123")
        }

        private var mockServer: ClientAndServer? = null

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            mockServer = ClientAndServer.startClientAndServer(8090);
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            mockServer!!.stop()
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
            .jsonPath("$.id").isNotEmpty
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

    @Test
    @Order(3)
    fun shouldFindOrganizationWithEmployees() {
        mockServer!!.`when`(request().withMethod("GET").withPath("/employees/organization/1"))
            .respond(response()
                .withStatusCode(200)
                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                .withBody(createEmployees()))

        webTestClient.get().uri("/organizations/1/with-employees").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.employees.length()").isEqualTo(2)
            .jsonPath("$.employees[0].id").isEqualTo(1)
            .jsonPath("$.employees[1].id").isEqualTo(2)
    }

    private fun createEmployees(): String {
        val employees: List<Employee> = listOf<Employee>(
            Employee(1, "Test1", 10000, 1),
            Employee(2, "Test2", 20000, 1)
        )
        return serializer.writeValueAsString(employees)
    }
}
