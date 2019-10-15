package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType

@Service
interface RatingService {
    fun setRating(userId: String, itemType: ItemType, itemId: String, rating: Float)
    fun clearRating(userId: String, itemType: ItemType, itemId: String)
}