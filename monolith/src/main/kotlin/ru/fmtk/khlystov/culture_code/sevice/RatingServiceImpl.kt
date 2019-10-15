package ru.fmtk.khlystov.culture_code.sevice

import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.repository.ratings.UserItemRatingRepository

class RatingServiceImpl(
        private val userItemRatingRepository: UserItemRatingRepository) : RatingService {

    override fun setRating(userId: String, itemType: ItemType, itemId: String, rating: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearRating(userId: String, itemType: ItemType, itemId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}