package pl.piomin.service.organization.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import pl.piomin.service.organization.model.Organization

interface OrganizationRepository : R2dbcRepository<Organization, Int> {
}