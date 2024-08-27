package com.example.musicplayermerk.presentation.view.songslist

import androidx.recyclerview.widget.DiffUtil
import com.example.musicplayermerk.domain.Song

class DiffUtilCallback(
    private val oldList: List<Song>,
    private val newList: List<Song>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].song == newList[newItemPosition].song
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].uri == newList[newItemPosition].uri -> false
            oldList[oldItemPosition].title == newList[newItemPosition].title -> false
            oldList[oldItemPosition].song == newList[newItemPosition].song -> false
            else -> true
        }
    }
}