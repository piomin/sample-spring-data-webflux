package pl.piomin.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.r2dbc.function.DatabaseClient
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableJdbcRepositories
class SampleApplication {

    @Autowired lateinit var databaseClient : DatabaseClient
    val CREATE_SQL = "CREATE TABLE IF NOT EXISTS employee (id serial PRIMARY KEY, name VARCHAR(100) NOT NULL, salary INTEGER NOT NULL);"

    @PostConstruct fun init () {
        println("init")
        val i = databaseClient.execute().sql(CREATE_SQL).fetch().rowsUpdated()
        println("init $i")
    }

}

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}