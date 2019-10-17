package ru.fmtk.khlystov.culture_code.repository.recommendations

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendation

interface RecommendationsRepository : MongoRepository<Recommendation, String>, RecommendationsRepositoryCustom {

    fun findAllByUserIdAndItemType(userId: String, itemType: ItemType, pageable: Pageable): List<Recommendation>
}

interface RecommendationsRepositoryCustom {
    fun setRecommendationsAsChecked(recommendations: Collection<Recommendation>)
}