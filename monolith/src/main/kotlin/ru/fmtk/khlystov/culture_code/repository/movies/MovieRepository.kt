package ru.fmtk.khlystov.culture_code.repository.movies

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import ru.fmtk.khlystov.culture_code.model.movies.Movie

@Repository
@RepositoryRestResource(path = "Movie")
interface MovieRepository : PagingAndSortingRepository<Movie, String>