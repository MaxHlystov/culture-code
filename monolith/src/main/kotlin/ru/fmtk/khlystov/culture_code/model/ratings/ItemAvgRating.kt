package ru.fmtk.khlystov.culture_code.model.ratings

data class ItemAvgRating(val itemType: ItemType,
                         val itemId: String,
                         val avgRating: Float)