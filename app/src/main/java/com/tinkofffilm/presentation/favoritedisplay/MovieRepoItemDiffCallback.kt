package com.tinkofffilm.presentation.favoritedisplay

import androidx.recyclerview.widget.DiffUtil
import com.tinkofffilm.data.pojo.MovieRepo

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
class MovieRepoItemDiffCallback : DiffUtil.ItemCallback<MovieRepo>() {
    override fun areItemsTheSame(oldItem: MovieRepo, newItem: MovieRepo): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: MovieRepo, newItem: MovieRepo): Boolean {
        return oldItem == newItem
    }
}