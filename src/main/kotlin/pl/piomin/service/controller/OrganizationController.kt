package pl.piomin.service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.piomin.service.model.Organization
import pl.piomin.service.repository.OrganizationRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/organization")
class OrganizationController {

    @Autowired
    lateinit var repository : OrganizationRepository

    @GetMapping
    fun findAll() : Flux<Organization> = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Int) : Mono<Organization> = repository.findById(id)

    @PostMapping
    fun add(@RequestBody employee: Organization) : Mono<Organization> = repository.save(employee)

}