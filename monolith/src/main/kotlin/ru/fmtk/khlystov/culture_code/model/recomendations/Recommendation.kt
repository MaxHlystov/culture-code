package ru.fmtk.khlystov.culture_code.model.recomendations

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import java.time.LocalDateTime

@Document(collection = "Recommendations_Recommendations")
@CompoundIndexes(CompoundIndex(name = "UserIdAndItemTypeAndId", def = "{'userId' : 1, 'itemType' : 1, 'itemId': 1}"),
        CompoundIndex(name = "ItemTypeAndId", def = "{'itemType' : 1, 'itemId': 1}"))
data class Recommendation(@Id val id: String?,
                          val userId: String,
                          val itemType: ItemType,
                          val itemId: String,
                          val checked: Boolean = false,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                          val checkedDate: LocalDateTime = LocalDateTime.now()) {

    fun getChecked(): Recommendation = if (this.checked) { this }
    else {
        Recommendation(id, userId, itemType, itemId, true, LocalDateTime.now())
    }
}