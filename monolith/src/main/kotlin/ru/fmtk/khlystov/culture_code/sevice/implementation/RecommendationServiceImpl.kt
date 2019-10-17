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
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationDTO

@Service
class RecommendationServiceImpl(
        private val recommendationsRepository: RecommendationsRepository,
        private val ratingService: RatingService,
        private val usersService: UsersService
) : RecommendationService {

    companion object {
        const val RECOMMENDATION_SERVICE__MIN_NEIGHBOURS_TO_COMPUTE = 2
    }

    override fun getRecommendations(userId: String, itemType: ItemType, number: Short): RecommendationDTO {
        val pageable = PageRequest.of(0, 5, Sort.by("rating").descending())
        return RecommendationDTO(userId, itemType,
                recommendationsRepository.findAllByUserIdAndItemType(userId, itemType, pageable)
                        .map(Recommendation::itemId).toSet())
    }

    override fun checkRecommendations(recommendations: Collection<RecommendationDTO>) {
        recommendationsRepository.setRecommendationsAsChecked()
    }

    /* Алгоритм расчета:
        1.   Определяем близость пользователей скалярным произведением векторов их оценок.
        2.   Если у пользователя оценок меньше порогового значения (10), рассчитываем предложения исходя
             из средних оценок позиций.
        3.   Для всех остальных определяем пять ближайших соседей (максимум коэффициента близости из п. 1).
        4.   По этим соседям усредняем оценки для позиций, которые пользователь еще не оценил, отбираем
             первые 10 максимальных, и записываем в таблицу рекомендаций.
    */
    override fun computeRecommendations() {
        val sort = Sort.by("id").ascending()
        val users = usersService.findAll(sort)
                .mapNotNull { user -> user.id }
                .toCollection(ArrayList<String>())
        val closeness = getUsersCloseness(users)
        val avgRatings = HashMap<ItemType, List<ItemAvgRating>>()
        for (itemType in ItemType.values()) {
            for (user in users) {
                val neighbours = closeness.getOrDefault(user, HashMap())
                if (neighbours.size < RECOMMENDATION_SERVICE__MIN_NEIGHBOURS_TO_COMPUTE) {

                } else {
                    // Search for items with the biggest average rating
                    if (!avgRatings.containsKey(itemType)) {
                        avgRatings[itemType] = ratingService.getAVGRatingsForItemType(itemType)

                    }
                }
            }
        }
    }

    fun getUsersCloseness(usersIds: ArrayList<String>): Map<String, Map<String, Float>> {
        val usersNumber = usersIds.size
        var closestUsers = HashMap<String, Map<String, Float>>()
        for (first in 0 until usersNumber) {
            val firstUserId = usersIds[first]
            val a = ratingService.getClosenessByRating(firstUserId)
//            closestUsers[firstUserId] = (first + 1 until usersNumber).asSequence()
//                    .map { idx -> usersIds[idx] }
//                    .map { secondUserId ->
//                        secondUserId to
//                                ratingService.getClosenessByRating(firstUserId)
//                    }
//                    .toMap()
        }
        return closestUsers
    }
}