package pl.piomin.service.employee.model

import org.springframework.data.annotation.Id

class Employee(val name: String, val salary: Int, val organizationId: Int) {

    @Id var id: Int? = null
}