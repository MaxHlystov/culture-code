package ru.fmtk.khlystov.culture_code.model.music

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Music_Artists")
data class MusicArtist(@Id val id: String?,
                       val name: String,
                       val website: String,
                       val posterUrl: String,
                       val description: String)