package ru.fmtk.khlystov.culture_code.model.movies

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

// Movie, Series
@Document(collection = "Movies_Types")
data class MovieType(@Id val id: String?, val name: String)