package ru.fmtk.khlystov.culture_code.repository.ratings

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.model.recomendations.TwoUsersCloseness
import java.util.*

@RepositoryRestResource(path = "UserItemRating")
interface UserItemRatingRepository : MongoRepository<UserItemRating, String>, UserItemRatingRepositoryCustom {
    @Query("{ 'itemType': ?0, 'rating': { \$gt: 0} }")
    fun findAllByItemType(itemType: ItemType): List<UserItemRating>

    fun findAllByUserIdAndItemTypeAndRatingGreaterThan(
            userId: String, itemType: ItemType, ratingGreaterThen: Float, pageable: Pageable): List<UserItemRating>
}

interface UserItemRatingRepositoryCustom {

    fun save(userItemRating: UserItemRating): Optional<UserItemRating>

    fun getAVGRatingsForItemType(itemType: ItemType, excludeUserId: String, limit: Long): List<ItemAvgRating>

    fun getAVGRatingsByUsersIds(itemType: ItemType,
                                excludeUserId: String,
                                usersIds: Collection<String>,
                                limit: Long): List<ItemAvgRating>

    fun getClosenessByRating(firstUserId: String, limit: Long): List<TwoUsersCloseness>
}