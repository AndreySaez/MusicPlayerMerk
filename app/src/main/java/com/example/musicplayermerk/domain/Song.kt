package com.example.musicplayermerk.domain

import android.net.Uri

data class Song(
    val song: Int,
    var title: String,
    var singer: String,
    val uri: Uri,
    val poster: String
)