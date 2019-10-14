package ru.fmtk.khlystov.culture_code.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import ru.fmtk.khlystov.culture_code.model.Person

@RepositoryRestResource(path = "Persons")
@Repository
interface PersonRepository : PagingAndSortingRepository<Person, String> {
}