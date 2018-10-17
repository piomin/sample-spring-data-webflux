package pl.piomin.service.organization.model

import org.springframework.data.annotation.Id

data class Employee(val name: String, val salary: Int, val organizationId: Int) {

    @Id var id: Int? = null
}