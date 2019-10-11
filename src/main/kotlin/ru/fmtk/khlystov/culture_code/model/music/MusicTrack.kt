package ru.fmtk.khlystov.culture_code.model.music

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import ru.fmtk.khlystov.culture_code.model.Person
import java.time.LocalDateTime

@Document(collection = "Music_Tracks")
data class MusicTrack(@Id val id: String,
                      val Title: String,
                      val year: Short,
                      @DBRef val album: MusicAlbum?,
                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                      val released: LocalDateTime,
                      val RuntimeSeconds: Int,
                      @DBRef val genres: Set<MusicGenre> = HashSet(),
                      @DBRef val labels: Set<MusicLabel> = HashSet(), // издатели
                      @DBRef val artist: MusicArtist, // исполнитель
                      @DBRef val composer: Person // композитор
)