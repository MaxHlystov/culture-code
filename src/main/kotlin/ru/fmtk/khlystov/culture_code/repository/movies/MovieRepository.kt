package ru.fmtk.khlystov.culture_code.repository.movies

import org.springframework.data.repository.PagingAndSortingRepository
import ru.fmtk.khlystov.culture_code.model.movies.Movie

interface MovieRepository : PagingAndSortingRepository<Movie, String>