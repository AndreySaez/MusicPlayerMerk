package com.example.musicplayermerk.presentation.view.songslist

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayermerk.R
import com.example.musicplayermerk.domain.Song

class SongsListAdapter(
    private val clickListener: (Song) -> Unit
) : RecyclerView.Adapter<SongsListViewHolder>() {
    private var songsList = listOf<Song>()
    private var selectedItemPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        return SongsListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_song_list, parent, false)
        )
    }

    override fun getItemCount(): Int = songsList.size

    private fun getItem(position: Int) = songsList[position]
    fun bindSongs(newSongsList: List<Song>) {
        val diffUtil = DiffUtilCallback(songsList, newSongsList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        songsList = newSongsList
        diffResults.dispatchUpdatesTo(this)
    }

    fun selectItem(song: Song) {
        setSingleSelection(songsList.indexOf(song))
    }

    private fun setSingleSelection(adapterPosition: Int) {
        if (adapterPosition == RecyclerView.NO_POSITION) return
        notifyItemChanged(selectedItemPos)
        selectedItemPos = adapterPosition
        notifyItemChanged(selectedItemPos)

    }


    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.bindData(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener(getItem(position))
        }
        if (selectedItemPos == position) {
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}

class SongsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val poster: ImageView = itemView.findViewById(R.id.poster)
    private val songName: TextView = itemView.findViewById(R.id.song_name)
    private val singers: TextView = itemView.findViewById(R.id.singers)

    fun bindData(song: Song) {
        poster.setImageBitmap(song.poster?.size?.let {
            BitmapFactory.decodeByteArray(song.poster, 0, it)
        })
        songName.text = song.title
        singers.text = song.singer

    }
}