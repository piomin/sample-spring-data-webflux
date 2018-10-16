package pl.piomin.service.dto

import pl.piomin.service.model.Employee

data class OrganizationDTO(var id: Int?, var name: String) {
    var employees : MutableList<EmployeeDTO> = ArrayList()
    constructor(employees: MutableList<EmployeeDTO>) : this(null, "") {
        this.employees = employees
    }
}