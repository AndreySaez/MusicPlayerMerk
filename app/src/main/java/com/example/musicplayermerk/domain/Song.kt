package com.example.musicplayermerk.domain

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

data class Song(
    val song: Int,
    val title: String,
    val singer: String,
    var uri: Uri,
    val poster: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Song

        if (title != other.title) return false
        if (singer != other.singer) return false
        if (uri != other.uri) return false
        if (poster != null) {
            if (other.poster == null) return false
            if (!poster.contentEquals(other.poster)) return false
        } else if (other.poster != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + singer.hashCode()
        result = 31 * result + uri.hashCode()
        result = 31 * result + (poster?.contentHashCode() ?: 0)
        return result
    }
}

object UriParse {
    fun uriFromSong(context: Context, song: Song): Uri {
        return Uri.parse(
            "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${song.song}"
        )
    }
}