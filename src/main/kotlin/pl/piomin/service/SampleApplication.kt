package pl.piomin.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@EnableJdbcRepositories
class SampleApplication

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}