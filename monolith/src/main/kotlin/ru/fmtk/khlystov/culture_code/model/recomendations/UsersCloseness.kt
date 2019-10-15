package ru.fmtk.khlystov.culture_code.model.recomendations

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Recommendations_UsersCloseness")
class UsersCloseness(@Id val id: String,
                     val firstUserId: String,
                     val secondUserId: String,
                     val closeness: Float) {
}