package ru.fmtk.khlystov.culture_code.repository.recommendations

import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendation

class RecommendationsRepositoryImpl(
        private val recommendationsRepository: RecommendationsRepository
) : RecommendationsRepositoryCustom {

    override fun setRecommendationsAsChecked(recommendations: Collection<Recommendation>) {
        recommendationsRepository.saveAll(recommendations.map(Recommendation::getChecked))

    }
}