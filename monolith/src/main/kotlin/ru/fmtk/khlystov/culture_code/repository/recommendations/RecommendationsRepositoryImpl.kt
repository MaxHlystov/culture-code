package ru.fmtk.khlystov.culture_code.repository.recommendations

import org.springframework.data.mongodb.core.MongoTemplate
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendation

class RecommendationsRepositoryImpl(private val mongoTemplate: MongoTemplate) : RecommendationsRepositoryCustom {

    override fun setRecommendationsAsChecked(recommendations: Collection<Recommendation>) {
        recommendations
                .map(Recommendation::getChecked)
                .forEach { recommendation ->
                    mongoTemplate.save(recommendation)
                }
    }
}