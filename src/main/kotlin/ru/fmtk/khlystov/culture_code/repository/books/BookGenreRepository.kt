package ru.fmtk.khlystov.culture_code.repository.books

import org.springframework.data.repository.PagingAndSortingRepository
import ru.fmtk.khlystov.culture_code.model.books.BookGenre

interface BookGenreRepository : PagingAndSortingRepository<BookGenre, String>