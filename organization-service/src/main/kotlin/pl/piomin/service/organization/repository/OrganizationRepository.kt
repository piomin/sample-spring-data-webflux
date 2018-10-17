package pl.piomin.service.organization.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import pl.piomin.service.organization.model.Organization

interface OrganizationRepository : ReactiveCrudRepository<Organization, Int> {
}