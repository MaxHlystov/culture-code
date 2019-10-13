package ru.fmtk.khlystov.culture_code.model.books

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import ru.fmtk.khlystov.culture_code.model.Person

@Document(collection = "Books_Books")
data class Book(@Id val id: String?,
                val Title: String,
                val year: Short,
                @DBRef val bookGenres: Set<BookGenre> = HashSet(),
                val writers: Set<Person> = HashSet(),
                @DBRef val isbnCodes: Set<String> = HashSet(),
                val posterUrl: String,
                val description: String)
