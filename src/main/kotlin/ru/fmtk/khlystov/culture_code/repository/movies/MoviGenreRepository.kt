package ru.fmtk.khlystov.culture_code.repository.movies

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.movies.MovieGenre

@RepositoryRestResource(path = "MovieGenre")
interface MovieGenreRepository : MongoRepository<MovieGenre, String>