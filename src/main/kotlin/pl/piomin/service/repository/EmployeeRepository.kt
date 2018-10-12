package pl.piomin.service.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import pl.piomin.service.model.Employee

interface EmployeeRepository : ReactiveCrudRepository<Employee, Int> {
}