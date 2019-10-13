package ru.fmtk.khlystov.culture_code.model.music

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import ru.fmtk.khlystov.culture_code.model.Country
import ru.fmtk.khlystov.culture_code.model.Person
import java.time.LocalDate

@Document(collection = "Music_Album")
data class MusicAlbum(@Id val id: String?,
                      val title: String,
                      val year: Short,
                      val tracks: Short,
                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                      val released: LocalDate,
                      val RuntimeSeconds: Int,
                      @DBRef val genres: Set<MusicGenre> = HashSet(),
                      @DBRef val labels: Set<MusicLabel> = HashSet(),
                      @DBRef val artist: MusicArtist, // исполнитель
                      @DBRef val composer: Person, // композитор
                      @DBRef val country: Country,
                      val website: String,
                      val posterUrl: String,
                      val description: String)