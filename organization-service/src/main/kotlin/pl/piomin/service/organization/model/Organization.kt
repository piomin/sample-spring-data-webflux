package pl.piomin.service.organization.model

import org.springframework.data.annotation.Id

class Organization(var name: String) {
    @Id
    var id: Int? = null
}