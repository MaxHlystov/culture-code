package ru.fmtk.khlystov.culture_code.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import ru.fmtk.khlystov.culture_code.model.Country

@RepositoryRestResource(path = "countries")
@Repository
interface CountryRepository : MongoRepository<Country, String>