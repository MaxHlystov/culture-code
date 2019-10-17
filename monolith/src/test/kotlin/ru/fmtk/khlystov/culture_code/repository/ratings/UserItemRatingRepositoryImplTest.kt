package ru.fmtk.khlystov.culture_code.repository.ratings

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import ru.fmtk.khlystov.culture_code.changelogs.InitTestMongoDBData
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.repository.AbstractRepositoryTest

@Import(value = [UserItemRatingRepositoryImpl::class, InitTestMongoDBData::class])
@SpringBootTest
@DisplayName("UserItemRatingRepository must")
internal class UserItemRatingRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var userItemRatingRepository: UserItemRatingRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    private var initTestMongoDBData = InitTestMongoDBData()

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserItemRatingRepositoryTest::class.java)
    }

    @BeforeEach
    fun initRaitings() {
        initTestMongoDBData.setUsersForTests(mongoTemplate)
        initTestMongoDBData.initUserRatingsForBooks(mongoTemplate)
        initTestMongoDBData.initUserRatingsForMovies(mongoTemplate)
    }

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
        assertTrue(avg.asSequence().zipWithNext()
                .all { (first, second) -> first.avgRating >= second.avgRating },
                "Ratings has to be ordered in decrease way.")
        assertEquals(computedAvg, avg.sortedBy(ItemAvgRating::itemId))
        log.info("Number of average ratings is ${avg.size}")
    }

    @DisplayName(" return closeness of users.")
    @Test
    fun getClosenessByRating() {
    }
}