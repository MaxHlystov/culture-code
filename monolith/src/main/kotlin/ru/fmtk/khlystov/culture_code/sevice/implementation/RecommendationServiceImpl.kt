package ru.fmtk.khlystov.culture_code.sevice.implementation

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendation
import ru.fmtk.khlystov.culture_code.repository.recommendations.RecommendationsRepository
import ru.fmtk.khlystov.culture_code.sevice.RatingService
import ru.fmtk.khlystov.culture_code.sevice.RecommendationService
import ru.fmtk.khlystov.culture_code.sevice.UsersService
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO

@Service
class RecommendationServiceImpl(
        private val recommendationsRepository: RecommendationsRepository,
        private val ratingService: RatingService,
        private val usersService: UsersService
) : RecommendationService {

    companion object {
        const val RECOMMENDATION_SERVICE__NEIGHBOURS_TO_COMPUTE = 5L
        const val RECOMMENDATION_SERVICE__RECOMMENDATIONS_NUMBER = 5L
    }

    override fun getRecommendations(userId: String, itemType: ItemType, number: Short): RecommendationsDTO {
        val pageable = PageRequest.of(0, 5, Sort.by("rating").descending())
        return RecommendationsDTO(userId, itemType,
                recommendationsRepository.findAllByUserIdAndItemType(userId, itemType, pageable)
                        .map(Recommendation::itemId).toSet())
    }

    override fun checkRecommendations(recommendations: Collection<Recommendation>) {
        //recommendationsRepository.setRecommendationsAsChecked(recommendations)
    }

    override fun computeRecommendations() {
        val users = usersService.findAllSortedById()
        for (user in users) {
            // 1. Find 5 neighbours for the user.
            val neighbours = ratingService.getClosenessByRating(user.id ?: "",
                    RECOMMENDATION_SERVICE__NEIGHBOURS_TO_COMPUTE)
                    .mapNotNull { twoUsersCloseness -> twoUsersCloseness.secondUserId }
            for (itemType in ItemType.values()) {
                val recommendationsToSave =
                        if (neighbours.size < RECOMMENDATION_SERVICE__NEIGHBOURS_TO_COMPUTE) {
                            // If user has less than 5 neighbours, take first 5 best items
                            // by average rating.
                            ratingService.getAVGRatingsForItemType(itemType, user.id?:"",
                                    RECOMMENDATION_SERVICE__RECOMMENDATIONS_NUMBER)
                        } else {
                            // Иначе, по этим соседям усредняем оценки для позиций, которые пользователь еще не оценил,
                            // отбираем первые 10 максимальных, и записываем в таблицу рекомендаций.
                            ratingService.getAVGRatingsByUsersIds(itemType, user.id?:"", neighbours,
                                    RECOMMENDATION_SERVICE__RECOMMENDATIONS_NUMBER)
                        }
                recommendationsRepository.saveAll(
                        recommendationsToSave.map { itemAvgRating ->
                            Recommendation(null, user.id ?: "", itemType, itemAvgRating.itemId) })
            }
        }
    }
}