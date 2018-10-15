package pl.piomin.service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.piomin.service.model.Employee
import pl.piomin.service.repository.EmployeeRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/employees")
class EmployeeController {

    @Autowired
    lateinit var repository : EmployeeRepository

    @GetMapping
    fun findAll() : Flux<Employee> {
        return repository.findAll()
    }

    @PostMapping
    fun add(@RequestBody employee: Employee) : Mono<Employee> {
        return repository.save(employee)
    }

}