package pl.piomin.service.organization

import org.junit.Test
import pl.piomin.service.organization.dto.OrganizationDTO
import pl.piomin.service.organization.model.Employee
import pl.piomin.service.organization.model.Organization
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class OrganizationUnitTests {

    @Test
    fun test() {
//        val ret : Mono<Flux<OrganizationDTO>> = getOrganizationByName("X")
//                .map { org -> getEmployees(org.id!!).map { emp -> OrganizationDTO(org.id as Int, org.name, emp) } }

        val ret : Mono<OrganizationDTO> = getOrganizationByName("X").zipWith(getEmployees(1).collectList()).map {
            tuple -> OrganizationDTO(tuple.t1.id as Int, tuple.t1.name, tuple.t2)
        }
        println("Mono: ${ret.block()!!.employees}")

    }

    fun getEmployees(organizationId: Int) : Flux<Employee> {
        return Flux.fromIterable(mutableListOf(Employee("X", 1, 1), Employee("Y", 2, 2)))
    }

    fun getOrganizationByName(name: String) : Mono<Organization> {
        val org = Organization(name)
        org.id = 1
        return Mono.just(org)
    }

}