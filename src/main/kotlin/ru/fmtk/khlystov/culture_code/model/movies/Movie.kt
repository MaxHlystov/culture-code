package ru.fmtk.khlystov.culture_code.model.movies

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Language
import ru.fmtk.khlystov.culture_code.model.Person
import java.time.LocalDateTime

@Document(collection = "Movies_Movies")
data class Movie(@Id val id: String,
                 val Title: String,
                 val type: MovieType,
                 val year: Short,
                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                 val released: LocalDateTime,
                 val RuntimeSeconds: Int,
                 val genres: List<MovieGenre>,
                 val director: Person,
                 val writers: List<Person>,
                 val actors: List<Person>,
                 val translators: List<Person>,
                 val languages: List<Language>,
                 val country: List<Country>,
                 val website: String,
                 val posterUrl: String,
                 val description: String)