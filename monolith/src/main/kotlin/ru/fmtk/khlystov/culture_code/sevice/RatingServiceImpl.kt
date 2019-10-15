package ru.fmtk.khlystov.culture_code.sevice

import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.repository.ratings.UserItemRatingRepository

class RatingServiceImpl(
        private val userItemRatingRepository: UserItemRatingRepository) : RatingService {

    override fun setRating(userId: String, itemType: ItemType, itemId: String, rating: Float): Boolean =
            userItemRatingRepository.save(UserItemRating(null, userId, itemType, itemId, rating))
                    .isPresent

    override fun clearRating(userId: String, itemType: ItemType, itemId: String): Boolean =
            setRating(userId, itemType, itemId, 0F)
}