package com.example.musicplayermerk.data

import android.net.Uri
import androidx.core.net.toUri
import com.example.musicplayermerk.R
import com.example.musicplayermerk.domain.Song
import java.io.File
import javax.inject.Inject

class SongDataSource @Inject constructor() {

    val songs: List<Song> = listOf(
        Song(
            R.raw.psy_opa_svo,
            "SVO",
            "Psy",
            Uri.fromFile(File("res/raw/psy_opa_svo.mp3")),
            null
        ),
        Song(
            R.raw.scally_milano_dubai,
            "Я не хочу жить в дубае",
            "Scally Milano",
            Uri.fromFile(File("res/raw/scally_milano_dubai.mp3")),
            null
        ),
        Song(
            R.raw.voskresenskii_money_and_sex,
            "Деньги и Секс",
            "Voskresenskii",
            R.raw.voskresenskii_money_and_sex.toString().toUri(),
            null
        ),
        Song(
            R.raw.zahotel,
            "Захотел",
            "Наверное Поэт",
            Uri.fromFile(File("res/raw/zahotel_trahat.mp3")),
            null
        )

    )

    fun getSongsData(): SongDataSource = SongDataSource()
}