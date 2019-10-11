package ru.fmtk.khlystov.culture_code.model.books

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Language
import ru.fmtk.khlystov.culture_code.model.Person

@Document(collection = "Books_Books")
data class Book(@Id val id: String,
                val Title: String,
                val year: Short,
                val bookGenres: Set<BookGenre> = HashSet(),
                val writers: Set<Person> = HashSet(),
                @DBRef val originalLanguage: Language,
                @DBRef val country: Country,
                @DBRef val translateLanguage: Language?,
                val translators: Set<Person> = HashSet(),
                val translationYear: Short,
                val originalBookId: String?,
                val isbnCodes: Set<String> = HashSet(),
                val posterUrl: String,
                val description: String)
