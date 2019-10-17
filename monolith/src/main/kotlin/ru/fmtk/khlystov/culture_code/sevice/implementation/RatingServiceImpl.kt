package ru.fmtk.khlystov.culture_code.sevice.implementation

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.model.recomendations.TwoUsersCloseness
import ru.fmtk.khlystov.culture_code.repository.ratings.UserItemRatingRepository
import ru.fmtk.khlystov.culture_code.sevice.RatingService

@Service
class RatingServiceImpl(
        private val userItemRatingRepository: UserItemRatingRepository) : RatingService {

    override fun setRating(userId: String, itemType: ItemType, itemId: String, rating: Float): Boolean =
            userItemRatingRepository.save(UserItemRating(null, userId, itemType, itemId, rating))
                    .isPresent

    override fun clearRating(userId: String, itemType: ItemType, itemId: String): Boolean =
            setRating(userId, itemType, itemId, 0F)

    override fun findAllByUserIdAndItemTypeAndRatingGreaterThan(
            userId: String, itemType: ItemType, ratingGreaterThen: Float, pageable: Pageable): List<UserItemRating> =
            userItemRatingRepository.findAllByUserIdAndItemTypeAndRatingGreaterThan(
                    userId, itemType, ratingGreaterThen, pageable)

    override fun getAVGRatingsForItemType(itemType: ItemType): List<ItemAvgRating> =
            userItemRatingRepository.getAVGRatingsForItemType(itemType)

    override fun getClosenessByRating(firstUserId: String): List<TwoUsersCloseness> =
            userItemRatingRepository.getClosenessByRating(firstUserId)
}