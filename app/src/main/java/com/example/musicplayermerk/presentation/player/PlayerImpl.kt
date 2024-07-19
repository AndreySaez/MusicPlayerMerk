package com.example.musicplayermerk.presentation.player

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayermerk.domain.Player
import com.example.musicplayermerk.domain.PlayerState
import com.example.musicplayermerk.domain.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class PlayerImpl @Inject constructor(
    private val context: Context
) : Player {

    override val nowPlaying: Flow<PlayerState>
        get() = _nowPlaying
            .asStateFlow()
            .filterNotNull()

    private var _nowPlaying = MutableStateFlow<PlayerState?>(null)
    override var songs: List<Song> = emptyList()
        set(value) {
            setSongsToPlayer(value)
            field = value
        }

    private val exoPlayer by lazy {
        ExoPlayer.Builder(context)
            .build().also { player ->
                player.addListener(
                    object : androidx.media3.common.Player.Listener {
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            player.currentMediaItem?.localConfiguration?.uri?.let {
                                updatePlayingInfo(it, isPlaying)
                            }

                        }
                    }
                )
            }
    }

    private fun updatePlayingInfo(currentTrack: Uri, isPlaying: Boolean) {
        val currentSong = songs.find { it.uri == currentTrack } ?: return
        val playerState = PlayerState(currentSong, isPlaying)
        _nowPlaying.value = playerState

    }


    private fun setSongsToPlayer(songs: List<Song>) {
        songs.map {

            it.uri = uriFromSong(context, it)
            MediaItem.fromUri(it.uri)
        }.also {
            exoPlayer.setMediaItems(it)
            exoPlayer.prepare()
        }
    }

    override fun play() {
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun next() {
        if (exoPlayer.hasNextMediaItem()) {
            exoPlayer.seekToNext()
        } else {
            exoPlayer.seekToDefaultPosition(0)
        }
    }

    override fun previous() {
        if (exoPlayer.hasPreviousMediaItem()) {
            exoPlayer.seekToPrevious()
        } else {
            exoPlayer.seekToDefaultPosition(exoPlayer.mediaItemCount - 1)
        }
    }

    companion object {
        private fun uriFromSong(context: Context, song: Song): Uri {
            return Uri.parse(
                "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${song.song}"
            )
        }
    }
}