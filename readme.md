## Introduction to Reactive APIs with Postgres, R2DBC, Spring Data and Spring WebFlux [![Twitter](https://img.shields.io/twitter/follow/piotr_minkowski.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/piotr_minkowski)

[![CircleCI](https://circleci.com/gh/piomin/sample-spring-data-webflux.svg?style=svg)](https://circleci.com/gh/piomin/sample-spring-data-webflux)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=piomin_sample-spring-data-webflux)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=piomin_sample-spring-data-webflux&metric=bugs)](https://sonarcloud.io/dashboard?id=piomin_sample-spring-data-webflux)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=piomin_sample-spring-data-webflux&metric=coverage)](https://sonarcloud.io/dashboard?id=piomin_sample-spring-data-webflux)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=piomin_sample-spring-data-webflux&metric=ncloc)](https://sonarcloud.io/dashboard?id=piomin_sample-spring-data-webflux)

Detailed description can be found here: [Introduction to Reactive APIs with Postgres, R2DBC, Spring Data JDBC and Spring WebFlux](https://piotrminkowski.com/2018/10/18/introduction-to-reactive-apis-with-postgres-r2dbc-spring-data-jdbc-and-spring-webflux/)

For the latest instruction how to use it with Spring Boot, Spring Data R2DBC, and Spring WebFlux see here: [Reactive Spring Boot with WebFlux, R2DBC and Postgres](https://piotrminkowski.com/2023/07/28/reactive-spring-boot-with-webflux-r2dbc-and-postgres/)

## Tech Stack

- **Spring Boot**: 4.0.4
- **Java**: 21
- **Kotlin**: 2.3.10
- **Spring WebFlux**: Reactive web framework
- **Spring Data R2DBC**: Reactive database connectivity
- **PostgreSQL**: Database with R2DBC driver
- **Testcontainers**: Docker-based integration testing
- **Maven**: Build tool

## Project Structure

This is a multi-module Maven project demonstrating reactive microservices architecture:

```
sample-spring-data-webflux/
├── employee-service/        # Employee management microservice
└── organization-service/    # Organization management microservice (aggregates employees)
```

### Employee Service

REST API for managing employees with reactive endpoints.

**Port**: 8090

**Endpoints**:
- `GET /employees` - Get all employees (returns `Flux<Employee>`)
- `GET /employees/{id}` - Get employee by ID (returns `Mono<Employee>`)
- `GET /employees/organization/{organizationId}` - Get employees by organization
- `POST /employees` - Create new employee

**Data Model**:
```kotlin
class Employee(
    val name: String,
    val salary: Int,
    val organizationId: Int
) {
    @Id var id: Int? = null
}
```

**Database**: PostgreSQL with R2DBC connection
- **URL**: `r2dbc:postgresql://localhost:5432/spring`
- **Table**: `employee` (id, name, salary, organization_id)

### Organization Service

REST API for managing organizations with inter-service communication using reactive WebClient.

**Port**: 8095

**Endpoints**:
- `GET /organizations` - Get all organizations (returns `Flux<Organization>`)
- `GET /organizations/{id}` - Get organization by ID (returns `Mono<Organization>`)
- `GET /organizations/{id}/with-employees` - Get organization with its employees (aggregated data)
- `POST /organizations` - Create new organization

**Data Model**:
```kotlin
class Organization(var name: String) {
    @Id var id: Int? = null
}
```

**Database**: PostgreSQL with R2DBC connection
- **URL**: `r2dbc:postgresql://localhost:5432/spring`
- **Table**: `organization` (id, name)

**Inter-Service Communication**: Uses reactive `WebClient` to fetch employees from employee-service

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.6+
- Docker (for Testcontainers in tests)
- PostgreSQL 14+ (or use Testcontainers for local development)

### Running the Application

1. **Start PostgreSQL database**:
   ```bash
   docker run -d \
     --name postgres \
     -e POSTGRES_DB=spring \
     -e POSTGRES_USER=spring \
     -e POSTGRES_PASSWORD=spring123 \
     -p 5432:5432 \
     postgres:14
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run Employee Service**:
   ```bash
   cd employee-service
   mvn spring-boot:run
   ```

4. **Run Organization Service** (in another terminal):
   ```bash
   cd organization-service
   mvn spring-boot:run
   ```

### Development Mode with Testcontainers

The project includes Testcontainers support for development mode. You can run the application with an embedded PostgreSQL container:

```bash
# Employee Service
cd employee-service
mvn spring-boot:test-run -Dspring-boot.run.mainClass=pl.piomin.service.employee.EmployeeApplicationTestKt

# Organization Service
cd organization-service
mvn spring-boot:test-run -Dspring-boot.run.mainClass=pl.piomin.service.organization.OrganizationApplicationTestKt
```

## Testing

The project includes comprehensive integration tests using:
- **JUnit 5**: Test framework
- **Testcontainers**: PostgreSQL containers for integration tests
- **WebTestClient**: Reactive web testing
- **MockServer**: Mocking HTTP endpoints

### Running Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl employee-service
mvn test -pl organization-service
```

**Test Coverage**:
- Employee Service: 4 integration tests
- Organization Service: 6 tests (1 unit test + 5 integration tests)

## Monitoring and Observability

Both services expose Spring Boot Actuator endpoints:

**Actuator Base Path**: `/actuator`

**Enabled Endpoints**:
- `/actuator/health` - Health check with detailed information
- `/actuator/metrics` - Application metrics
- `/actuator/env` - Environment properties
- And more (all endpoints exposed via `management.endpoints.web.exposure.include: '*'`)

## Example API Usage

### Create an Organization
```bash
curl -X POST http://localhost:8095/organizations \
  -H "Content-Type: application/json" \
  -d '{"name": "Tech Corp"}'
```

### Create an Employee
```bash
curl -X POST http://localhost:8090/employees \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "salary": 75000, "organizationId": 1}'
```

### Get Organization with Employees
```bash
curl http://localhost:8095/organizations/1/with-employees
```

Response:
```json
{
  "id": 1,
  "name": "Tech Corp",
  "employees": [
    {
      "id": 1,
      "name": "John Doe",
      "salary": 75000,
      "organizationId": 1
    }
  ]
}
```

## Key Features

✅ **Fully Reactive**: Non-blocking I/O throughout the stack using Project Reactor
✅ **R2DBC**: Reactive database connectivity with PostgreSQL
✅ **Kotlin**: Concise and expressive code
✅ **Spring Boot 4**: Latest Spring Boot with enhanced reactive support
✅ **Microservices**: Two independent services communicating reactively
✅ **Testcontainers**: Real database integration testing
✅ **Service Discovery**: WebClient-based inter-service communication
✅ **Production Ready**: Actuator endpoints for monitoring

## Migration Notes

### Spring Boot 4 Compatibility

This project has been updated to Spring Boot 4.0.4. Key changes include:

1. **WebTestClient Configuration**: No longer auto-configured by default. Manual bean configuration added in test classes.
2. **WebClient.Builder**: Requires explicit bean definition (not auto-configured).
3. **Test Dependencies**: Added `reactor-test` and `spring-boot-test-autoconfigure` for proper reactive testing support.
4. **JUnit 5**: Migrated from JUnit 4 to JUnit 5 (`org.junit.jupiter.api.Test`).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is available for educational and demonstration purposes.
