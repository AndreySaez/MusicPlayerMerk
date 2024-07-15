package com.example.musicplayermerk.presentation.view

import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import coil.load
import com.example.musicplayermerk.R
import com.example.musicplayermerk.di.appComponent
import com.example.musicplayermerk.domain.Song
import com.example.musicplayermerk.presentation.playerViewModel.PlayerViewModel
import com.example.musicplayermerk.presentation.playerViewModel.ViewModelFactory
import com.example.musicplayermerk.presentation.state.ListState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlayerFragment : Fragment() {
    private lateinit var player: ExoPlayer
    private val viewModel by viewModels<PlayerViewModel> { viewmodelFactory }
    private lateinit var title: TextView
    private lateinit var artist: TextView
    private lateinit var poster: ImageView
    private lateinit var next: ImageView
    private lateinit var previous: ImageView
    private lateinit var start: ImageView
    private lateinit var stop: ImageView

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(requireContext()).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.tv_title)
        artist = view.findViewById(R.id.tv_artist)
        poster = view.findViewById(R.id.picture)
        next = view.findViewById(R.id.iv_next)
        previous = view.findViewById(R.id.iv_previous)
        start = view.findViewById(R.id.iv_start)
        stop = view.findViewById(R.id.iv_stop)

        next.setOnClickListener {
            if (player.hasNextMediaItem()) {
                player.seekToNext()
                player.prepare()
                player.play()
            }
        }
        previous.setOnClickListener {
            if (player.hasPreviousMediaItem()) {
                player.seekToPrevious()
                player.prepare()
                player.play()
            }
        }

        start.setOnClickListener {
            player.prepare()
            player.play()
            start.isVisible = false
            stop.isVisible = true
        }
        stop.setOnClickListener {
            stop.isVisible = false
            start.isVisible = true
            player.stop()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.songList.onEach { state ->
            if (state is ListState.SongList) {
                val list = mutableListOf<MediaItem>()
                for (i in state.items.indices) {
                    val path =
                        "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context?.packageName}/${state.items[i].song}"
                    list.add(MediaItem.fromUri(path))
                    player.addMediaItem(list[i])
                }
                bindSongData(state.items[player.currentMediaItemIndex])
            }
        }.launchIn(lifecycleScope)
    }

    private fun bindSongData(song: Song) {
        title.text = song.title
        artist.text = song.singer
        poster.load(song.poster)
    }
}