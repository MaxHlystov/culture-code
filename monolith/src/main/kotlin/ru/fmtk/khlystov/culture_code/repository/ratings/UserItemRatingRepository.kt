package ru.fmtk.khlystov.culture_code.repository.ratings

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import java.util.*

@RepositoryRestResource(path = "UserItemRating")
interface UserItemRatingRepository : MongoRepository<UserItemRating, String>, UserItemRatingRepositoryCustom {
    fun findAllByUserIdAndItemTypeAndRatingGreaterThan(
            userId: String, itemType: ItemType, ratingGreaterThen: Float, pageable: Pageable): Set<UserItemRating>
}

interface UserItemRatingRepositoryCustom {
    fun save(userItemRating: UserItemRating): Optional<UserItemRating>

    fun getAVGRatingsForItemType(itemType: ItemType): List<ItemAvgRating>
    fun getClosestByRating(firstUserId: String, secondUserId: String): Float
}