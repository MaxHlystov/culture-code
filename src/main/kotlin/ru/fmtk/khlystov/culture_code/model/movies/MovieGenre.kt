package ru.fmtk.khlystov.culture_code.model.movies

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Movies_Genres")
data class MovieGenre (@Id val id: String?,
                       @Indexed(unique=true) val name: String)