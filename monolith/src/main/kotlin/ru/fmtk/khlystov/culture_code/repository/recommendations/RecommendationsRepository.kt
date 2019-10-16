package ru.fmtk.khlystov.culture_code.repository.recommendations

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendations

interface RecommendationsRepository : MongoRepository<Recommendations, String> {

    fun findAllByUserIdAndItemType(userId: String, itemType: ItemType, pageable: Pageable): List<Recommendations>
}