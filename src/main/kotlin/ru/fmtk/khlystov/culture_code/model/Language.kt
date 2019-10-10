package ru.fmtk.khlystov.culture_code.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Main_Languages")
data class Language(@Id val id: String?, val name: String)