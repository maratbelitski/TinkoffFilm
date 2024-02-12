package com.tinkofffilm.presentation.maindisplay.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.tinkofffilm.R
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.presentation.maindisplay.MovieItemDiffCallback
import com.tinkofffilm.presentation.maindisplay.MovieItemHolder

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
class MovieAdapterFavorite : ListAdapter<Movie, MovieItemHolder>(MovieItemDiffCallback()) {
    companion object {
        private const val FAVORITE = 1
        private const val NO_FAVORITE = 0
        private const val DEFAULT_RATING = "0.0"
        private const val DEFAULT_SIZE = 0
    }


    var onMovieItemClick: ((Movie) -> Unit)? = null
    var onStarFavoriteLongClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {

        var layout = R.layout.item_movie
        if (viewType == FAVORITE) {
            layout = R.layout.item_movie_favorite
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MovieItemHolder(view)
    }

    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {

        if (getItem(position).ratingKinopoisk != null) {
            val rating = getItem(position).ratingKinopoisk.toDouble()
            holder.ratingMovie.text = getItem(position).ratingKinopoisk
            if (rating >= 8) {
                holder.ratingMovie.setBackgroundResource(R.drawable.cercle_green)
            } else if (rating in 4.0..6.0) {
                holder.ratingMovie.setBackgroundResource(R.drawable.cercle_yellow)
            } else if (rating in 1.0..5.0) {
                holder.ratingMovie.setBackgroundResource(R.drawable.cercle_red)
            }
        } else {
            holder.ratingMovie.text = DEFAULT_RATING
            holder.ratingMovie.setBackgroundResource(R.drawable.cercle_white)
        }


        holder.nameMovie.text = getItem(position).nameRu
        holder.dateMovie.text = getItem(position).year
        holder.genreMovie.text = getItem(position).genres?.get(DEFAULT_SIZE).toString()

        val movie = getItem(position)
        Glide.with(holder.itemView)
            .load(movie.posterUrl)
            .into(holder.posterMovie)


        holder.itemView.setOnLongClickListener {
            onStarFavoriteLongClick?.invoke(getItem(position))
            true
        }

        holder.itemView.setOnClickListener {
            onMovieItemClick?.invoke(getItem(position))
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).favorite == FAVORITE) {
            FAVORITE
        } else {
            NO_FAVORITE
        }
    }
}