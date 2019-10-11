package ru.fmtk.khlystov.culture_code.model.movies

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Language
import ru.fmtk.khlystov.culture_code.model.Person
import java.time.LocalDateTime

@Document(collection = "Movies_Movies")
data class Movie(@Id val id: String,
                 val Title: String,
                 @DBRef val type: MovieType,
                 val year: Short,
                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                 val released: LocalDateTime,
                 val RuntimeSeconds: Int,
                 @DBRef val genres: Set<MovieGenre> = HashSet(),
                 @DBRef val director: Person,
                 @DBRef val actors: Set<Person> = HashSet(),
                 @DBRef val translators: Set<Person> = HashSet(),
                 @DBRef val languages: Set<Language> = HashSet(),
                 @DBRef val country: Country,
                 val website: String,
                 val posterUrl: String,
                 val description: String)