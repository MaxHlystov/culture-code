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

    override fun getAVGRatingsForItemType(itemType: ItemType, excludeUserId: String, limit: Long): List<ItemAvgRating> =
            userItemRatingRepository.getAVGRatingsForItemType(itemType, excludeUserId, limit)

    override fun getAVGRatingsByUsersIds(itemType: ItemType,
                                         excludeUserId: String,
                                         usersIds: Collection<String>,
                                         limit: Long): List<ItemAvgRating> =
            userItemRatingRepository.getAVGRatingsByUsersIds(itemType, excludeUserId, usersIds, limit)

    override fun getClosenessByRating(userId: String, limit: Long): List<TwoUsersCloseness> =
            userItemRatingRepository.getClosenessByRating(userId, limit)
}