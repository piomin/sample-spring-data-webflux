package pl.piomin.service.controller

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
    fun findAll() : Flux<EmployeeDTO> = repository.findAll().map { t -> EmployeeDTO(t.id, t.name, t.salary, t.organizationId) }

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Int) : Mono<Employee> = repository.findById(id)

    @GetMapping("/organization/{organizationId}")
    fun findByorganizationId(@PathVariable organizationId : Int) : Flux<Employee> = repository.findByOrganizationId(organizationId)

    @PostMapping
    fun add(@RequestBody employee: Employee) : Mono<Employee> = repository.save(employee)

}