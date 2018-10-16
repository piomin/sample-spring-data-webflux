package pl.piomin.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import pl.piomin.service.model.Employee
import pl.piomin.service.model.Organization
import pl.piomin.service.repository.OrganizationRepository
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest
class HelloTest {

    @Autowired
    lateinit var repository: OrganizationRepository

    @Test
    fun testRepository() {
        val org = Organization("Test1")
        val employee = Employee("Test", 1000)
        org.addEmployee(employee)
        repository.save(org)
    }
}
