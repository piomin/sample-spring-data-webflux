package pl.piomin.service.employee.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.piomin.service.dto.EmployeeDTO
import pl.piomin.service.model.Employee
import pl.piomin.service.repository.EmployeeRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.LongConsumer

@RestController
@RequestMapping("/employees")
class EmployeeController {

    @Autowired
    lateinit var repository : EmployeeRepository

    @GetMapping
    fun findAll() : Flux<Employee> = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Int) : Mono<Employee> = repository.findById(id)

    @GetMapping("/organization/{organizationId}")
    fun findByorganizationId(@PathVariable organizationId : Int) : Flux<Employee> = repository.findByOrganizationId(organizationId)

    @PostMapping
    fun add(@RequestBody employee: Employee) : Mono<Employee> = repository.save(employee)

}