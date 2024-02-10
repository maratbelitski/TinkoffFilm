package com.tinkofffilm.presentation.maindisplay

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tinkofffilm.R

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
class MovieItemHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val posterMovie: ImageView = itemView.findViewById(R.id.poster_movie)
    val ratingMovie: TextView = itemView.findViewById(R.id.rating_movie)
    val nameMovie: TextView = itemView.findViewById(R.id.tvName_movie)
    val dateMovie: TextView = itemView.findViewById(R.id.tvDate_movie)
    val genreMovie: TextView = itemView.findViewById(R.id.tvGenre)
}
