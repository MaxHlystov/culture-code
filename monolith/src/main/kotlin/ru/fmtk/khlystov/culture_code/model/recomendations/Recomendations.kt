package ru.fmtk.khlystov.culture_code.model.recomendations

import ru.fmtk.khlystov.culture_code.model.ratings.ItemType

data class Recomendations(val userId: String,
                          val itemType: ItemType,
                          val itemsId: Set<String>) {
}