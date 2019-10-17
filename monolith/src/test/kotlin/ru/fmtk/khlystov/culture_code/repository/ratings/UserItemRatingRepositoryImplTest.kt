package ru.fmtk.khlystov.culture_code.repository.ratings

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating

@Import(value = [UserItemRatingRepositoryImpl::class])
@SpringBootTest
@ExtendWith(SpringExtension::class)
@DisplayName("UserItemRatingRepository must")
internal class UserItemRatingRepositoryTest { //: AbstractRepositoryTest() {

    @Autowired
    lateinit var userItemRatingRepository: UserItemRatingRepository

    @DisplayName(" return average ratings by types of items.")
    @Test
    fun getAVGRatingsForItemType() {
        val allRatings = userItemRatingRepository.findAllByItemType(ItemType.BOOK)
        val computedAvg = allRatings.groupBy { ratingItem -> ratingItem.itemType to ratingItem.itemId }
                .map { (idPair, ratingItems) ->
                    ItemAvgRating(idPair.first, idPair.second,
                            ratingItems.map(UserItemRating::rating).average().toFloat())
                }
                .sortedBy(ItemAvgRating::itemId)
        val avg = userItemRatingRepository.getAVGRatingsForItemType(ItemType.BOOK)
        //assertEquals(computedAvg.size, avg.size, " It needs to have ratings for all items.")
        assertTrue(avg.asSequence().zipWithNext()
                .all { (first, second) -> first.avgRating >= second.avgRating },
                "Ratings has to be ordered in decrease way.")
        assertEquals(computedAvg, avg.sortedBy(ItemAvgRating::itemId))
    }

    @DisplayName(" return closeness of users.")
    @Test
    fun getClosenessByRating() {
    }
}