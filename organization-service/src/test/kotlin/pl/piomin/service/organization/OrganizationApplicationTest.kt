package pl.piomin.service.organization

import org.springframework.boot.fromApplication
import org.springframework.boot.with

class OrganizationApplicationTest

fun main(args: Array<String>) {
    fromApplication<OrganizationApplication>().with(PostgresContainerDevMode::class).run(*args)
}