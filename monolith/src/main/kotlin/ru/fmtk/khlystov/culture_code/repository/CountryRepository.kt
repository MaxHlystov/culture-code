package ru.fmtk.khlystov.culture_code.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import ru.fmtk.khlystov.culture_code.model.Country

@RepositoryRestResource(path = "countries")
@Repository
interface CountryRepository : PagingAndSortingRepository<Country, String>