package ru.fmtk.khlystov.culture_code.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Main_Persons")
data class Person(@Id val id: String?,
                  val fullName: String,
                  val shortBio: String)