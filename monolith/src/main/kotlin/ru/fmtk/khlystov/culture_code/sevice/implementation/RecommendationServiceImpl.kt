package ru.fmtk.khlystov.culture_code.sevice.implementation

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendations
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

    override fun getRecommendations(userId: String, itemType: ItemType, number: Short): RecommendationsDTO {
        val pageable = PageRequest.of(0, 5, Sort.by("rating").descending())
        return RecommendationsDTO(userId, itemType,
                recommendationsRepository.findAllByUserIdAndItemType(userId, itemType, pageable)
                        .map(Recommendations::itemId).toSet())
    }

    override fun computeRecommendations() {
        val sort = Sort.by("id").descending()
        val users = usersService.findAll(sort)
                .mapNotNull { user -> user.id }
                .toCollection(ArrayList<String>())
        val closeness = getUsersCloseness(users)
        val avgRatings = HashMap<ItemType, List<ItemAvgRating>>()
        for (itemType in ItemType.values()) {
            for (user in users) {
                if (closeness.containsKey(user)) {
                    val neighbors = closeness[user]

                } else {
                    // Search for items with the biggest average rating
                    if (!avgRatings.containsKey(itemType)) {
                        avgRatings[itemType] = ratingService.getAVGRatingsForItemType(itemType)

                    }
                }
            }
        }
    }

    private fun getUsersCloseness(usersIds: ArrayList<String>): Map<String, Map<String, Float>> {
        val usersNumber = usersIds.size
        var closestUsers = HashMap<String, Map<String, Float>>()
        for (first in 0 until usersNumber) {
            val firstUserId = usersIds[first]
            closestUsers[firstUserId] = (first + 1 until usersNumber).asSequence()
                    .map { idx -> usersIds[idx] }
                    .map { secondUserId ->
                        secondUserId to
                                ratingService.getClosenessByRating(firstUserId, secondUserId)
                    }
                    .toMap()
        }
        return closestUsers
    }
}