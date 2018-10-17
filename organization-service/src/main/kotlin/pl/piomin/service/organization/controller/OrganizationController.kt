package pl.piomin.service.organization.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import pl.piomin.service.organization.dto.OrganizationDTO
import pl.piomin.service.organization.mapper.EmployeeMapper
import pl.piomin.service.organization.model.Employee
import pl.piomin.service.organization.model.Organization
import pl.piomin.service.organization.repository.OrganizationRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/organizations")
class OrganizationController {

    @Autowired
    lateinit var repository : OrganizationRepository
    @Autowired
    lateinit var clientBuilder : WebClient.Builder

    @GetMapping
    fun findAll() : Flux<Organization> = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Int) : Mono<Organization> = repository.findById(id)

    @GetMapping("/{id}/withEmployees")
    fun findByIdWithEmployees(@PathVariable id : Int) : Mono<OrganizationDTO> {
        val employees = clientBuilder.build().get().uri("http://localhost:8090/employees/organization/$id")
                .retrieve().bodyToFlux(Employee::class.java)
        return employees.collectList()
                .map { a -> OrganizationDTO(a) }
                .mergeWith { repository.findById(id) }
                .collectList()
                .map { a -> EmployeeMapper().map(a) }
    }

    @PostMapping
    fun add(@RequestBody employee: Organization) : Mono<Organization> = repository.save(employee)

}