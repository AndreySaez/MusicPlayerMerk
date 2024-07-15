package com.example.musicplayermerk.data

import android.net.Uri
import androidx.core.net.toUri
import com.example.musicplayermerk.R
import com.example.musicplayermerk.domain.Song
import java.io.File

class SongDataSource {

    val songs: List<Song> = listOf(
        Song(
            R.raw.psy_opa_svo,
            "SVO",
            "Psy",
            Uri.fromFile(File("res/raw/psy_opa_svo.mp3")),
            "https://n1s2.hsmedia.ru/04/3a/7a/043a7a05c46ec85dcce4a861ed1d006f/1200x630_0xac120003_2985818291660227758.png"
        ),
        Song(
            R.raw.scally_milano_dubai,
            "Я не хочу жить в дубае",
            "Scally Milano",
            Uri.fromFile(File("res/raw/scally_milano_dubai.mp3")),
            "https://the-flow.ru/uploads/images/resize/830x0/adaptiveResize/14/56/07/19/41/da3040240cd7.jpg"
        ),
        Song(
            R.raw.voskresenskii_money_and_sex,
            "Деньги и Секс",
            "Voskresenskii",
            R.raw.voskresenskii_money_and_sex.toString().toUri(),
            "https://the-flow.ru/uploads/images/resize/830x0/adaptiveResize/04/81/57/89/13/156005c40cd7.jpg"
        ),
        Song(
            R.raw.zahotel,
            "Захотел",
            "Наверное Поэт",
            Uri.fromFile(File("res/raw/zahotel_trahat.mp3")),
            "https://cdn.britannica.com/96/1296-050-4A65097D/gelding-bay-coat.jpg"
        )

    )
}