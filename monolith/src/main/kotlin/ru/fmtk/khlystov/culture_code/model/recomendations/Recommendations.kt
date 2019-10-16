package ru.fmtk.khlystov.culture_code.model.recomendations

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import java.time.LocalDateTime

@Document(collection = "Recommendations_Recommendations")
@CompoundIndexes(CompoundIndex(name = "itemTypeAndId", def = "{'userId' : 1, 'itemType' : 1, 'itemId': 1}"))
data class Recommendations(@Id val id: String,
                           val userId: String,
                           val itemType: ItemType,
                           val itemId: String,
                           val checked: Boolean,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           val checkedDate: LocalDateTime) {
}