package ru.fmtk.khlystov.culture_code.sevice.dto

import ru.fmtk.khlystov.culture_code.model.ratings.ItemType

data class RatingsSet(val itemType: ItemType,
                      val itemsRatings: Map<String, Float>)