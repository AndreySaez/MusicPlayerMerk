package com.example.musicplayermerk.presentation.view.songslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayermerk.R
import com.example.musicplayermerk.appComponent
import com.example.musicplayermerk.presentation.di.songslistdi.DaggerSongsListComponent
import com.example.musicplayermerk.presentation.viewmodel.playerViewModel.ViewModelFactory
import com.example.musicplayermerk.presentation.viewmodel.songslistviewmodel.SongListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SongsListFragment : Fragment() {
    private val viewModel by viewModels<SongListViewModel> { viewmodelFactory }

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: SongsListAdapter

    override fun onAttach(context: Context) {
        DaggerSongsListComponent.factory().create(
            context.appComponent,
            requireActivity()
        ).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_songs_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recycler_view)
        adapter = SongsListAdapter {
            viewModel.seekToSong(it)
        }
        recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        viewModel.currentState.onEach { state ->
            adapter.bindSongs(state.songs)
            adapter.selectItem(state.currentSong)
        }.launchIn(lifecycleScope)
    }
}