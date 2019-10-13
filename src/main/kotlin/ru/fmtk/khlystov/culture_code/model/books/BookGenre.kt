package ru.fmtk.khlystov.culture_code.model.books

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Books_Genres")
data class BookGenre(@Id val id: String?,
                     @Indexed(unique=true) val name: String,
                     val group: String = "") {
    override fun toString(): String = if (group.isEmpty()) {
        name
    } else {
        "$group: $name"
    }
}