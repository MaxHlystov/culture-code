package ru.fmtk.khlystov.culture_code.changelogs

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.fmtk.khlystov.culture_code.model.User
import ru.fmtk.khlystov.culture_code.model.books.Book
import ru.fmtk.khlystov.culture_code.model.movies.Movie
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.security.Roles

class InitTestMongoDBData {

    var passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
    lateinit var usersInDB: List<User>

    fun setUsersForTests(template: MongoTemplate) {
        val password = passwordEncoder.encode("111111")
        val userRoles = setOf(Roles.User.toString())
        val users = (1..10).map { idx ->
            User(null, "Test$idx", password = password, roles = userRoles)
        }.toList()
        usersInDB = template.insertAll<User>(users)
                .filter { user -> user.id != null }
                .toList()
    }

    fun initUserRatingsForBooks(template: MongoTemplate) {
        val allBooks = template.findAll(Book::class.java).filter { book -> book.id != null }
        val booksNumber = allBooks.size
        val ratings = usersInDB.mapIndexed { index, user -> index to user }
                .flatMap { (index, user) ->
                    val numberOfRatings = index % booksNumber
                    allBooks.take(numberOfRatings).map { book ->
                        UserItemRating(null,
                                user.id ?: "",
                                ItemType.BOOK,
                                book.id ?: "",
                                (index % 6).toFloat())
                    }
                }
        template.insertAll<UserItemRating>(ratings)
    }

    fun initUserRatingsForMovies(template: MongoTemplate) {
        val allMovies = template.findAll(Movie::class.java).filter { movie -> movie.id != null }
        val moviesNumber = allMovies.size
        val ratings = usersInDB.mapIndexed { index, user -> index to user }
                .flatMap { (index, user) ->
                    val numberOfRatings = index % moviesNumber
                    allMovies.take(numberOfRatings).map { movie ->
                        UserItemRating(null,
                                user.id ?: "",
                                ItemType.MOVIE,
                                movie.id ?: "",
                                (index % 6).toFloat())
                    }
                }
        template.insertAll<UserItemRating>(ratings)
    }
}