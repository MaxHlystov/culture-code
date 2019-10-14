package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recomendations

@Service
interface RecomendationService {
    fun getRecommendations(userId: String, itemType: ItemType, number: Short): Recomendations
    fun computeRecomendations()
}