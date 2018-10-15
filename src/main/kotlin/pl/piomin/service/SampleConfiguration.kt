package pl.piomin.service

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import pl.piomin.service.repository.EmployeeRepository

@Configuration
class SampleConfiguration {

    @Bean
    fun repository(factory: R2dbcRepositoryFactory): EmployeeRepository {
        return factory.getRepository(EmployeeRepository::class.java)
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

}