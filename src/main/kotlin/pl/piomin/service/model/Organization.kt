package pl.piomin.service.model

import org.springframework.data.annotation.Id

data class Organization(var name: String) {

    @Id
    var id: Int? = null
    var employees : MutableSet<Employee> = HashSet()

    fun addEmployee(employee: Employee) {
        employees.add(employee)
    }

}