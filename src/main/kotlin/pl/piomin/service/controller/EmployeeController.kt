package pl.piomin.service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.piomin.service.model.Employee
import pl.piomin.service.repository.EmployeeRepository
import reactor.core.publisher.Flux

@RestController
class EmployeeController {

    @Autowired
    lateinit var repository : EmployeeRepository

    @GetMapping
    fun findAll() : Flux<Employee> {
        return repository.findAll()
    }

}