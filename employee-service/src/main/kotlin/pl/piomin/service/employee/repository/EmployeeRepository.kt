package pl.piomin.service.employee.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import pl.piomin.service.employee.model.Employee
import reactor.core.publisher.Flux

interface EmployeeRepository : R2dbcRepository<Employee, Int> {
//    @Query("select id, name, salary, organization_id from employee e where e.organization_id = $1")
    fun findByOrganizationId(organizationId: Int) : Flux<Employee>
}