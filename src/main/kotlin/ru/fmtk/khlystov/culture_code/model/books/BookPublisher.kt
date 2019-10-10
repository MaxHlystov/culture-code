package ru.fmtk.khlystov.culture_code.model.books

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Books_Publishers")
data class BookPublisher (@Id val id: String?, val name: String)