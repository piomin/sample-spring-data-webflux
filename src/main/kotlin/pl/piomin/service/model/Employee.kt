package pl.piomin.service.model

import org.springframework.data.annotation.Id

data class Employee(val name: String, val salary: Int) {

    @Id var id: Int? = null
}