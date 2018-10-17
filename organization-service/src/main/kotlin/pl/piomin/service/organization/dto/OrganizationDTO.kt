package pl.piomin.service.organization.dto

import pl.piomin.service.model.Employee

data class OrganizationDTO(var id: Int?, var name: String) {
    var employees : MutableList<Employee> = ArrayList()
    constructor(employees: MutableList<Employee>) : this(null, "") {
        this.employees = employees
    }
}