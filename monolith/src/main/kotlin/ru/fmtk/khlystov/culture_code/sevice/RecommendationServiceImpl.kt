package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendations
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.repository.ratings.UserItemRatingRepository
import ru.fmtk.khlystov.culture_code.repository.recommendations.RecommendationsRepository
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO

@Service
class RecommendationServiceImpl(
        private val recommendationsRepository: RecommendationsRepository,
        private val userItemRatingRepository: UserItemRatingRepository,
        private val userRepository: UserRepository
) : RecommendationService {

    override fun getRecommendations(userId: String, itemType: ItemType, number: Short): RecommendationsDTO {
        val pageable = PageRequest.of(0, 5, Sort.by("rating").descending())
        return RecommendationsDTO(userId, itemType,
                recommendationsRepository.findAllByUserIdAndItemType(userId, itemType, pageable)
                        .map(Recommendations::itemId).toSet())
    }

    override fun computeRecommendations() {
        val sort = Sort.by("id").descending()
        val users = userRepository.findAll(sort)
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
                        avgRatings[itemType] = userItemRatingRepository.getAVGRatingsForItemType(itemType)

                    }
                }
            }
        }
    }

    private fun getUsersCloseness(usersIds: ArrayList<String>, numberOfUsers: Int = 5): Map<String, Map<String, Float>> {
        val usersNumber = usersIds.size
        var closestUsers = HashMap<String, HashMap<String, Float>>()
        for (first in 0 until usersNumber) {
            val firstUserId = usersIds[first]
            closestUsers[firstUserId] = (first until usersNumber).asSequence()
                        .map { idx -> usersIds[idx] }
                        .map { secondUserId ->
                            secondUserId to
                                    userItemRatingRepository.getClosestByRating(firstUserId, secondUserId)
                        }
                        .sortedByDescending { (_, closeness) -> closeness }
                        .toMap()
            }
        }
        return closestUsers
    }
}