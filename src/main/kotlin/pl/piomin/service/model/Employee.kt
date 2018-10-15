package pl.piomin.service.model

import org.springframework.data.annotation.Id

data class Employee(@Id val id: Int, val name: String, val salary: Int) {
}