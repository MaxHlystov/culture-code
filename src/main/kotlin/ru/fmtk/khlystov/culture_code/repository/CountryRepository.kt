package ru.fmtk.khlystov.culture_code.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.Country

@RepositoryRestResource(path = "Countries")
interface CountryRepository : PagingAndSortingRepository<Country, String>