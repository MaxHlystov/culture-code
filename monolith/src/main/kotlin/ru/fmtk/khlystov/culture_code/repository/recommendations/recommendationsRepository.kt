package ru.fmtk.khlystov.culture_code.repository.recommendations

import org.springframework.data.mongodb.repository.MongoRepository
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendations

interface recommendationsRepository: MongoRepository<Recommendations, String> {
}