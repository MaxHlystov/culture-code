package ru.fmtk.khlystov.culture_code.repository.ratings

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.repository.AbstractRepositoryTest

@Import(value = [UserItemRatingRepositoryImpl::class])
@DisplayName("UserItemRatingRepository must")
internal class UserItemRatingRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var userItemRatingRepository: UserItemRatingRepository

//    @Autowired
//    lateinit var bookRepository: BookRepository
//
//    @Autowired
//    lateinit var movieRepository: MovieRepository
//
//    lateinit var allBooks: Map<String, Book>
//    lateinit var allMovies: Map<String, Movie>
//
//    @BeforeEach
//    internal fun beforeEach() {
//        allBooks = bookRepository.findAll()
//                .filter { book -> book.id != null }
//                .associate { book -> (book.id ?: "") to book }
//        allMovies = movieRepository.findAll()
//                .filter { movie -> movie.id != null }
//                .associate { movie -> (movie.id ?: "") to movie }
//    }

    @DisplayName(" return average ratings by types of items.")
    @Test
    fun getAVGRatingsForItemType() {
        val allRatings = userItemRatingRepository.findAllByItemType(ItemType.BOOK)
        val computedAvg = allRatings.groupBy { ratingItem -> ratingItem.itemType to ratingItem.itemId }
                .map { (idPair, ratingItems) ->
                    ItemAvgRating(idPair.first, idPair.second,
                            ratingItems.map(UserItemRating::rating).average().toFloat())
                }
        val avg = userItemRatingRepository.getAVGRatingsForItemType(ItemType.BOOK)
        assertEquals(5, avg.size)
        assertTrue(avg.asSequence().zipWithNext()
                .all { (first, second) -> first.avgRating >= second.avgRating },
                "Ratings has to be ordered in decrease way.")
        assertEquals(computedAvg, avg)
    }

    @DisplayName(" return closeness of users.")
    @Test
    fun getClosenessByRating() {
    }
}