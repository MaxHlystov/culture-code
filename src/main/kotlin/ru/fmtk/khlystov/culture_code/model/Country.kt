package ru.fmtk.khlystov.culture_code.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Main_Countries")
data class Country(@Id val id: String?,
                   val name: String,
                   @Indexed(unique=true) val alpha3: String,
                   @Indexed(unique=true) val iso: Short)