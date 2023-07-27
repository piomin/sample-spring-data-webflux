package pl.piomin.service.employee

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
//@EnableJdbcRepositories
class EmployeeApplication

fun main(args: Array<String>) {
    runApplication<EmployeeApplication>(*args)
}