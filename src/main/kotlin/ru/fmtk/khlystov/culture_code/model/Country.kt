package ru.fmtk.khlystov.culture_code.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Main_Countries")
data class Country(@Id val id: String?,
                   val name: String,
                   val alpha3: String,
                   val iso: Short)