package pl.piomin.service

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent
import pl.piomin.service.model.Employee
import pl.piomin.service.repository.EmployeeRepository
import pl.piomin.service.repository.OrganizationRepository
import java.util.concurrent.atomic.AtomicInteger

@Configuration
class SampleConfiguration {

    val employeeId : AtomicInteger = AtomicInteger(0)

    @Bean
    fun employeeRepository(factory: R2dbcRepositoryFactory): EmployeeRepository {
        return factory.getRepository(EmployeeRepository::class.java)
    }

    @Bean
    fun organizationRepository(factory: R2dbcRepositoryFactory): OrganizationRepository {
        return factory.getRepository(OrganizationRepository::class.java)
    }

    @Bean
    fun factory(client: DatabaseClient): R2dbcRepositoryFactory {
        val context = RelationalMappingContext()
        context.afterPropertiesSet()
        return R2dbcRepositoryFactory(client, context)
    }

    @Bean
    fun databaseClient(factory: ConnectionFactory): DatabaseClient {
        return DatabaseClient.builder().connectionFactory(factory).build()
    }

    @Bean
    fun connectionFactory(): PostgresqlConnectionFactory {
        val config = PostgresqlConnectionConfiguration.builder() //
                .host("192.168.99.100") //
                .port(5432) //
                .database("reactive") //
                .username("reactive") //
                .password("reactive123") //
                .build()

        return PostgresqlConnectionFactory(config)
    }

//    @Bean
//    open fun beforeSaveEventApplicationListener() : ApplicationListener<BeforeSaveEvent> {
//        return ApplicationListener { event ->
//            println("Event: $event")
//            if (event.entity is Employee) {
//                val employee = event.entity as Employee
//                employee.id = employeeId.incrementAndGet()
//            }
//        }
//    }

    @Bean
    open fun beforeSaveEventApplicationListener() : ApplicationListener<*> {
        return object : ApplicationListener<BeforeSaveEvent> {
            override fun onApplicationEvent(event: BeforeSaveEvent) {
                println("Event: $event")
                if (event.entity is Employee) {
                    val employee = event.entity as Employee
                    employee.id = employeeId.incrementAndGet()
                }
            }
        }
    }
}