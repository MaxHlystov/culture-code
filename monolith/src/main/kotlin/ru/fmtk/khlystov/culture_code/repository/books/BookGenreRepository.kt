package ru.fmtk.khlystov.culture_code.repository.books

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.books.BookGenre

@RepositoryRestResource(path = "BookGenre")
interface BookGenreRepository : PagingAndSortingRepository<BookGenre, String>