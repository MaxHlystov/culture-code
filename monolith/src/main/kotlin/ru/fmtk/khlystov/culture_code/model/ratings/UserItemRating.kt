package ru.fmtk.khlystov.culture_code.model.ratings

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import ru.fmtk.khlystov.culture_code.model.User

@Document(collection = "Ratings_UserItemRating")
@CompoundIndexes(CompoundIndex(name = "itemTypeAndId", def = "{'itemType' : 1, 'itemId': 1}"))
data class UserItemRating(@Id val id: String?,
                          @DBRef val userId: String,
                          val itemType: ItemType,
                          val itemId: String,
                          val rating: Short)