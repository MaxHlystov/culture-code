package ru.fmtk.khlystov.culture_code.model.books

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Language
import ru.fmtk.khlystov.culture_code.model.Person

@Document(collection = "Books_Books")
data class Book(@Id val id: String,
                val Title: String,
                val year: Short,
                val bookGenres: List<BookGenre>,
                val writers: List<Person>,
                val originalLanguage: Language,
                val country: Country,
                val translateLanguage: Language?,
                val translators: List<Person>,
                val translationYear: Short,
                val originalBookId: String?,
                val isbnCodes: List<String>,
                val posterUrl: String,
                val description: String)
