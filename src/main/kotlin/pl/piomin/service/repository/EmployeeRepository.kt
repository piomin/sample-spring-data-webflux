package pl.piomin.service.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import pl.piomin.service.model.Employee
import reactor.core.publisher.Flux

interface EmployeeRepository : ReactiveCrudRepository<Employee, Int> {
    fun findByOrganizationId(organizationId: Int) : Flux<Employee>
}