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
class UserItemRatingRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var userItemRatingRepository: UserItemRatingRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    private var initTestMongoDBData = InitTestMongoDBData()

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserItemRatingRepositoryTest::class.java)
    }

    @BeforeEach
    fun initRatings() {
        initTestMongoDBData.run(mongoTemplate)
    }

    @DisplayName(" return average ratings for items of the type and except the user items.")
    @Test
    fun getAVGRatingsForItemType() {
        val allRatings = userItemRatingRepository.findAllByItemType(ItemType.BOOK)
        val excludeUserId = allRatings.first().userId
        val excludeItemsIds = allRatings
                .filter { userItemRating -> userItemRating.userId == excludeUserId }
                .map { userItemRating -> userItemRating.itemId }.toSet()
        val computedAvg = allRatings.filter { ratingItem -> !excludeItemsIds.contains(ratingItem.itemId) }
                .groupBy { ratingItem -> ratingItem.itemType to ratingItem.itemId }
                .map { (idPair, ratingItems) ->
                    ItemAvgRating(idPair.first, idPair.second,
                            ratingItems.map(UserItemRating::rating).average().toFloat())
                }
                .sortedByDescending(ItemAvgRating::avgRating)
        val avg = userItemRatingRepository.getAVGRatingsForItemType(
                ItemType.BOOK, excludeUserId, allRatings.size.toLong())
        assertTrue(avg.asSequence().zipWithNext()
                .all { (first, second) -> first.avgRating >= second.avgRating },
                "Ratings has to be ordered in decrease way.")
        assertEquals(computedAvg, avg) //.sortedBy(ItemAvgRating::itemId))
        log.info("Number of average ratings is ${avg.size}")
    }

    @DisplayName(" return average ratings for items of the type and rated by the users and except the user items.")
    @Test
    fun getAVGRatingsByUsersIds() {
        val allRatings = userItemRatingRepository.findAllByItemType(ItemType.BOOK)
        val allUsersIds = allRatings.distinctBy(UserItemRating::userId).map(UserItemRating::userId)
        val excludeUserId = allUsersIds.firstOrNull() ?: ""
        val excludeItemsIds = allRatings
                .filter { userItemRating -> userItemRating.userId == excludeUserId }
                .map { userItemRating -> userItemRating.itemId }.toSet()
        val rightUsersId = allUsersIds.asSequence()
                .filter { id -> id != excludeUserId }.take(1).toSet()
        val computedAvg = allRatings
                .filter { ratingItem ->
                    !excludeItemsIds.contains(ratingItem.itemId) && rightUsersId.contains(ratingItem.userId)
                }
                .groupBy { ratingItem -> ratingItem.itemType to ratingItem.itemId }
                .map { (idPair, ratingItems) ->
                    ItemAvgRating(idPair.first, idPair.second,
                            ratingItems.map(UserItemRating::rating).average().toFloat())
                }
                .sortedByDescending(ItemAvgRating::avgRating)
        val avg = userItemRatingRepository.getAVGRatingsByUsersIds(
                ItemType.BOOK, excludeUserId, rightUsersId, allRatings.size.toLong())
        assertTrue(avg.asSequence().zipWithNext()
                .all { (first, second) -> first.avgRating >= second.avgRating },
                "Ratings has to be ordered in decrease way.")
        assertEquals(computedAvg, avg) //.sortedBy(ItemAvgRating::itemId))
        log.info("Number of average ratings is ${avg.size}")
    }

    @DisplayName(" return closeness of users.")
    @Test
    fun getClosenessByRating() {
        val allRatings = userItemRatingRepository.findAll().filterNotNull()
        val userId = allRatings.first().userId
        val userCloseness = userItemRatingRepository.getClosenessByRating(userId, allRatings.size.toLong())
        assertEquals(15, userCloseness.size)
    }
}