package com.tinkofffilm.presentation

import androidx.recyclerview.widget.DiffUtil
import com.tinkofffilm.data.Movie

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
class MovieItemDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}