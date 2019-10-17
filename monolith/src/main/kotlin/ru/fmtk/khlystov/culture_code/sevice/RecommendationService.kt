package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationDTO

@Service
interface RecommendationService {

    fun getRecommendations(userId: String, itemType: ItemType, number: Short): RecommendationDTO

    fun checkRecommendations(recommendations: Collection<RecommendationDTO>)

    fun computeRecommendations()
}