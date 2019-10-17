package ru.fmtk.khlystov.culture_code.sevice.dto

import ru.fmtk.khlystov.culture_code.model.ratings.ItemType

data class RecommendationDTO(val userId: String,
                             val itemType: ItemType,
                             val itemsId: Set<String>)