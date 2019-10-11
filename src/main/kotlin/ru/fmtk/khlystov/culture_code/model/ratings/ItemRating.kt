package ru.fmtk.khlystov.culture_code.model.ratings

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import ru.fmtk.khlystov.culture_code.model.ItemType
import ru.fmtk.khlystov.culture_code.model.User

@Document(collection = "Ratings_UserItemRating")
data class UserItemRating(@Id val id: String?,
                          @DBRef val user: User,
                          val itemType: ItemType,
                          val rating: Short)