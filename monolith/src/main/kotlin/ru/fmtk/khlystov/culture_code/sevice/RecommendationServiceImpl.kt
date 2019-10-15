package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.repository.recommendations.recommendationsRepository
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO

@Service
class RecommendationServiceImpl(private val recommendationsRepository: recommendationsRepository) : RecommendationService {
    override fun getRecommendations(userId: String, itemType: ItemType, number: Short): RecommendationsDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun computeRecommendations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}