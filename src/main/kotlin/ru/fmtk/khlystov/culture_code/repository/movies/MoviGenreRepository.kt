package ru.fmtk.khlystov.culture_code.repository.movies

import org.springframework.data.repository.PagingAndSortingRepository
import ru.fmtk.khlystov.culture_code.model.movies.MovieGenre

interface MovieGenreRepository : PagingAndSortingRepository<MovieGenre, String>