package pl.piomin.service.organization

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class OrganizationApplication {

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer? {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        return initializer
    }

    @Value("\${employee.client.url}")
    private lateinit var employeeUrl: String

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder.baseUrl(employeeUrl).build()
    }
}

fun main(args: Array<String>) {
    runApplication<OrganizationApplication>(*args)
}