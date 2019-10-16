package ru.fmtk.khlystov.culture_code.repository.books

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.books.Book

@RepositoryRestResource(path = "Book")
interface BookRepository : MongoRepository<Book, String> {

}