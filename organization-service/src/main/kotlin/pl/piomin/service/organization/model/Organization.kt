package pl.piomin.service.organization.model

import org.springframework.data.annotation.Id

data class Organization(var name: String) {

    @Id
    var id: Int? = null

}