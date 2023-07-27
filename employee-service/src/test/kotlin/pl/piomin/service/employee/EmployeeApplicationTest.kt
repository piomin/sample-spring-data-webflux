package pl.piomin.service.employee

import org.springframework.boot.fromApplication
import org.springframework.boot.with

class EmployeeApplicationTest

fun main(args: Array<String>) {
    fromApplication<EmployeeApplication>().with(PostgresContainerDevMode::class).run(*args)
}