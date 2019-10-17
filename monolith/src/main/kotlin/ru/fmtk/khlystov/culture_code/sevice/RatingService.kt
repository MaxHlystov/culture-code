package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.model.recomendations.TwoUsersCloseness

@Service
interface RatingService {
    fun setRating(userId: String, itemType: ItemType, itemId: String, rating: Float): Boolean
    fun clearRating(userId: String, itemType: ItemType, itemId: String): Boolean
    fun findAllByUserIdAndItemTypeAndRatingGreaterThan(
            userId: String, itemType: ItemType, ratingGreaterThen: Float, pageable: Pageable): List<UserItemRating>
    fun getAVGRatingsForItemType(itemType: ItemType): List<ItemAvgRating>
    fun getClosenessByRating(firstUserId: String): List<TwoUsersCloseness>
}