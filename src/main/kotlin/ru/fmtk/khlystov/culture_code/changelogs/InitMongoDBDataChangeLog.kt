package ru.fmtk.khlystov.culture_code.changelogs

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.books.BookGenre

@ChangeLog(order = "001")
class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "initBookGenres", author = "khlystov", runAlways = false)
    fun initBookGenres(template: MongoTemplate) {
        getListsOfBookGenres().forEach { (group, sequence) ->
            sequence.forEach { genre -> template.save<BookGenre>(BookGenre(null, genre, group)) }
        }
    }

    @ChangeSet(order = "001", id = "initCountries", author = "khlystov", runAlways = false)
    fun initCountries(template: MongoTemplate) {
        getListOfCountries().forEach { (name, alpha3, iso) ->
            template.save<Country>(Country(null, name, alpha3, iso.toShort()))
        }
    }

    @ChangeSet(order = "002", id = "initMovieGenres", author = "khlystov", runAlways = false)
    fun initMovieGenres(template: MongoTemplate) {
        getListsOfMovieGenres().forEach { genre -> template.save<BookGenre>(BookGenre(null, genre)) }
    }


//    @ChangeSet(order = "002", id = "initBooks", author = "khlystov", runAlways = false)
//    fun initBooks(template: MongoTemplate) {
//        template.save<Book>(Book(null,
//                "Пушкин А.,Лерма",
//                "Евгений Онегин.Герой нашего времени","978-5-8475-1147-6",2019,"МИРОВАЯ ХУДОЖЕСТВЕННАЯ ЛИТЕРАТУРА"
//        ))
//    }
}