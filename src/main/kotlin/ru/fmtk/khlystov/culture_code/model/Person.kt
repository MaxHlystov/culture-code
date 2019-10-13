package ru.fmtk.khlystov.culture_code.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Document(collection = "Main_Persons")
data class Person(@Id val id: String?,
                  val fullName: String,
                  val shortBio: String,
                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                  val birthday: LocalDateTime)