package ru.fmtk.khlystov.culture_code.model.music

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Person
import java.time.LocalDateTime

@Document(collection = "Music_Album")
data class MusicAlbum(@Id val id: String,
                      val Title: String,
                      val year: Short,
                      val tracks: Short,
                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                      val released: LocalDateTime,
                      val RuntimeSeconds: Int,
                      val genres: List<MusicGenre>,
                      val labels: List<MusicLabel>,
                      val artist: MusicArtist, // исполнитель
                      val composer: Person, // композитор
                      val country: List<Country>,
                      val website: String,
                      val posterUrl: String,
                      val description: String)