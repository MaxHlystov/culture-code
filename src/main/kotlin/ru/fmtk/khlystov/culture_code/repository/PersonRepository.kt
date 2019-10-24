package ru.fmtk.khlystov.culture_code.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import ru.fmtk.khlystov.culture_code.model.Person

@RepositoryRestResource(path = "persons")
@Repository
interface PersonRepository : MongoRepository<Person, String>