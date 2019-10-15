package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendations
import ru.fmtk.khlystov.culture_code.repository.recommendations.RecommendationsRepository
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO

@Service
class RecommendationServiceImpl(
        private val recommendationsRepository: RecommendationsRepository
) : RecommendationService {

    override fun getRecommendations(userId: String, itemType: ItemType, number: Short): RecommendationsDTO {
        val pageable = PageRequest.of(0, 5, Sort.by("rating").descending())
        return RecommendationsDTO(userId, itemType,
                recommendationsRepository.findAllByUserIdAndItemType(userId, itemType, pageable)
                        .map(Recommendations::itemId).toSet())
    }

    override fun computeRecommendations() {

    }
}