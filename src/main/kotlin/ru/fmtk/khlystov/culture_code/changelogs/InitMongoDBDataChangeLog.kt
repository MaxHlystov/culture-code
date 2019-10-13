package ru.fmtk.khlystov.culture_code.changelogs

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Person
import ru.fmtk.khlystov.culture_code.model.books.Book
import ru.fmtk.khlystov.culture_code.model.books.BookGenre
import ru.fmtk.khlystov.culture_code.model.movies.MovieGenre
import ru.fmtk.khlystov.culture_code.model.music.MusicGenre

@ChangeLog(order = "001")
class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "initBookGenres", author = "khlystov", runAlways = false)
    fun initBookGenres(template: MongoTemplate) {
        template.insertAllWithTransform(getListsOfBookGenres().asSequence()) { (group, sequence) ->
            sequence.map { genre -> BookGenre(null, genre, group) }
        }
    }

    @ChangeSet(order = "001", id = "initCountries", author = "khlystov", runAlways = false)
    fun initCountries(template: MongoTemplate) {
        template.insertAllWithTransform(getListOfCountries()) { (name, alpha3, iso) ->
            Country(null, name, alpha3, iso.toShort())
        }
    }

    @ChangeSet(order = "002", id = "initMovieGenres", author = "khlystov", runAlways = false)
    fun initMovieGenres(template: MongoTemplate) {
        template.insertAllWithTransform(getListsOfMovieGenres()) { genre ->
            MovieGenre(null, genre)
        }
    }

    @ChangeSet(order = "003", id = "initMusicGenres", author = "khlystov", runAlways = false)
    fun initMusicGenres(template: MongoTemplate) {
        template.insertAllWithTransform(getListOfMusicGenres()) { genre ->
            template.save(MusicGenre(null, genre))
        }
    }

    @ChangeSet(order = "004", id = "initBooks", author = "khlystov", runAlways = false)
    fun initBooks(template: MongoTemplate) {
        val genres = template.findAll(BookGenre::class.java).groupBy(BookGenre::name)
        val savedAuthors = template.insertAllWithTransform(ListOfBooks.authors.asSequence()) { (_, author) -> author }
                .groupBy(Person::fullName)
        template.insertAllWithTransform(ListOfBooks.books.asSequence()) { book ->
            book.copyWithSubstitute(genres, savedAuthors)
        }
    }
}

private fun <T, R> MongoTemplate.insertAllWithTransform(sequence: Sequence<T>,
                                                        transform: (value: T) -> R): Collection<R> =
        this.insertAll(sequence.map(transform).toList())

private fun Book.copyWithSubstitute(genres: Map<String, List<BookGenre>>,
                                    persons: Map<String, List<Person>>): Book {
    return Book(this.id,
            this.title,
            this.year,
            this.genres.map { genre -> genres.getFirstOrDefault(genre.name, genre) }.toSet(),
            this.writers.map { person -> persons.getFirstOrDefault(person.fullName, person) }.toSet(),
            this.posterUrl,
            this.description
    )
}

private fun <ID, T> Map<ID, List<T>>.getFirstOrDefault(id: ID, default: T): T {
    val items: List<T>? = this[id]
    if (items == null || items.size == 0) {
        return default
    }
    return items[0]
}