package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.model.User
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
        val users = userRepository.findAll(sort).toList()
        val closeness = getUsersCloseness(users)
        for(user in users) {
            if(closeness.containsKey(user)) {
                val neighbors = closeness[user]

            } else {
                // Search for items with the biggest average rating
                for(itemType in ItemType.values()) {
                    val avgRatings = userItemRatingRepository.getAVGRatingsForItemType(itemType)
                }
            }
        }

    }

    private fun getUsersCloseness(users: List<User>, numberOfUsers: Int = 5): Map<User, List<User>> {
        return HashMap()
    }
}