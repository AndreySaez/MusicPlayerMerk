package com.example.musicplayermerk.presentation.view.player

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.musicplayermerk.R
import com.example.musicplayermerk.appComponent
import com.example.musicplayermerk.presentation.di.playerdi.DaggerPlayerComponent
import com.example.musicplayermerk.presentation.view.songslist.SongsListFragment
import com.example.musicplayermerk.presentation.viewmodel.playerViewModel.PlayerViewModel
import com.example.musicplayermerk.presentation.viewmodel.playerViewModel.ViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlayerFragment : Fragment() {
    private val viewModel by viewModels<PlayerViewModel> { viewmodelFactory }
    private lateinit var views: Views

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    override fun onAttach(context: Context) {
        DaggerPlayerComponent.factory().create(
            context.appComponent,
            requireActivity()
        ).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_player, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = Views(view)
        views.listIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.main, SongsListFragment())
                .addToBackStack(null)
                .commit()
        }

        views.next.setOnClickListener {
            viewModel.next()
        }
        views.previous.setOnClickListener {
            viewModel.previous()
        }

        views.start.setOnClickListener {
            viewModel.play()
        }
        views.stop.setOnClickListener {
            viewModel.stop()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.currentState.onEach { state ->
            bindTrackInfo(state.song.uri)
            views.seekBar.max = state.songDuration.toInt()
            views.start.isVisible = !state.isPlaying
            views.stop.isVisible = state.isPlaying
            views.seekBar.progress = state.currentTime.toInt()
        }.launchIn(lifecycleScope)
    }

    private fun bindTrackInfo(uri: Uri) {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, uri)
        val image = retriever.embeddedPicture
        val decodeImage = image?.let { BitmapFactory.decodeByteArray(image, 0, it.size) }
        views.title.text = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        views.artist.text =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        views.poster.setImageBitmap(decodeImage)

    }

    class Views(view: View) {
        val listIcon: ImageView = view.findViewById(R.id.list_icon)
        val seekBar: SeekBar = view.findViewById(R.id.seekbar)
        val next: ImageView = view.findViewById(R.id.iv_next)
        val title: TextView = view.findViewById(R.id.tv_title)
        val artist: TextView = view.findViewById(R.id.tv_artist)
        var poster: ImageView = view.findViewById(R.id.picture)
        val previous: ImageView = view.findViewById(R.id.iv_previous)
        val start: ImageView = view.findViewById(R.id.iv_start)
        val stop: ImageView = view.findViewById(R.id.iv_stop)
    }
}
