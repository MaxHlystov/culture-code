package ru.fmtk.khlystov.culture_code.model.music

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Music_Genres")
data class MusicGenre (@Id val id: String?,
                       @Indexed(unique=true) val name: String)