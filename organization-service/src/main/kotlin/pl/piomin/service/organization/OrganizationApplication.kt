package pl.piomin.service.organization

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@EnableJdbcRepositories
class OrganizationApplication

fun main(args: Array<String>) {
    runApplication<OrganizationApplication>(*args)
}