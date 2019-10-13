package ru.fmtk.khlystov.culture_code.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.Person

@RepositoryRestResource(path = "Persons")
interface PersonRepository : PagingAndSortingRepository<Person, String> {
}