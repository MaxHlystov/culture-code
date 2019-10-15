package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recomendations

@Service
interface RecomendationService {
    // Returns recommendations for given user and item type.
    // Number - is the count of recommendations requested.
    fun getRecommendations(userId: String, itemType: ItemType, number: Short): Recomendations

    // Mark recommendations as seen.
    fun checkRecommendation(userId: String, itemType: ItemType, itemsIds: Set<String>)

    // Starts process of compute recommendations.
    fun computeRecomendations(userId: String)
}