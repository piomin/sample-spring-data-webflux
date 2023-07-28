package pl.piomin.service.employee.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import pl.piomin.service.employee.model.Employee
import reactor.core.publisher.Flux

interface EmployeeRepository : R2dbcRepository<Employee, Int> {
    fun findByOrganizationId(organizationId: Int) : Flux<Employee>
}