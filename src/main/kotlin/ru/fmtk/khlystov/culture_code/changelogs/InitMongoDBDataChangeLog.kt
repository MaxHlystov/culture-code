package ru.fmtk.khlystov.culture_code.changelogs

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate
import ru.fmtk.khlystov.culture_code.changelogs.collections.*
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Person
import ru.fmtk.khlystov.culture_code.model.books.Book
import ru.fmtk.khlystov.culture_code.model.books.BookGenre
import ru.fmtk.khlystov.culture_code.model.movies.Movie
import ru.fmtk.khlystov.culture_code.model.movies.MovieGenre
import ru.fmtk.khlystov.culture_code.model.music.MusicGenre


@ChangeLog(order = "001")
class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "initBookGenres", author = "khlystov", runAlways = false)
    fun initBookGenres(template: MongoTemplate) {
        val seqOfBooks = getListsOfBookGenres().asSequence()
                .flatMap { (group, sequence) ->
                    sequence.map { genre -> BookGenre(null, genre, group) }
                }
        template.insertAllWithTransform(seqOfBooks) { genre -> genre }
    }

    @ChangeSet(order = "001", id = "initCountries", author = "khlystov", runAlways = false)
    fun initCountries(template: MongoTemplate) {
        template.insertAllWithTransform(getListOfCountries()) { country -> country }
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
            MusicGenre(null, genre)
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

    @ChangeSet(order = "005", id = "initMovies", author = "khlystov", runAlways = false)
    fun initMovies(template: MongoTemplate) {
        val genres = template.findAll(MovieGenre::class.java).groupBy(MovieGenre::name)
        val countries = template.findAll(Country::class.java).groupBy(Country::alpha3)
        val savedPersons = template.insertAllWithTransform(ListOfMovies.persons.asSequence()) { (_, person) -> person }
                .groupBy(Person::fullName)
        template.insertAllWithTransform(ListOfMovies.movies.asSequence()) { movie ->
            movie.copyWithSubstitute(genres, countries, savedPersons)
        }
    }

}

private fun <T, R> MongoTemplate.insertAllWithTransform(sequence: Sequence<T>,
                                                        transform: (value: T) -> R): Collection<R> {
    val l = sequence.map(transform).toList()
    return this.insertAll<R>(l)
}

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

private fun Movie.copyWithSubstitute(genres: Map<String, List<MovieGenre>>,
                                     countries: Map<String, List<Country>>,
                                     persons: Map<String, List<Person>>): Movie {
    return Movie(this.id,
            this.title,
            this.type,
            this.year,
            this.released,
            this.RuntimeSeconds,
            this.genres.map { genre -> genres.getFirstOrDefault(genre.name, genre) }.toSet(),
            persons.getFirstOrDefault(this.director.fullName, this.director),
            this.actors.map { actor -> persons.getFirstOrDefault(actor.fullName, actor) }.toSet(),
            countries.getFirstOrException(this.country.alpha3),
            this.website,
            this.posterUrl,
            this.description)
}

private fun <ID, T> Map<ID, List<T>>.getFirstOrDefault(id: ID, default: T): T {
    val items: List<T>? = this[id]
    if (items == null || items.isEmpty()) {
        return default
    }
    return items[0]
}

private fun <ID, T> Map<ID, List<T>>.getFirstOrException(id: ID): T {
    val items: List<T>? = this[id]
    require(!(items == null || items.isEmpty())) { "Didn't find item with id $id in list of type ${this::class.simpleName}." }
    return items[0]
}

