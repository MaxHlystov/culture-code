package ru.fmtk.khlystov.culture_code.repository.recommendations

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendation

@RepositoryRestResource(exported = false)
interface RecommendationsRepository : MongoRepository<Recommendation, String>, RecommendationsRepositoryCustom {

    fun findAllByUserIdAndItemTypeAndCheckedFalse(userId: String, itemType: ItemType, pageable: Pageable): List<Recommendation>
}

interface RecommendationsRepositoryCustom {
    fun setRecommendationsAsChecked(recommendations: Collection<Recommendation>)
}