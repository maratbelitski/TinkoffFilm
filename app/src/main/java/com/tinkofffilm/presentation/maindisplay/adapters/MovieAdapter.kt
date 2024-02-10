package com.tinkofffilm.presentation.maindisplay.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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
class MovieAdapter : ListAdapter<Movie, MovieItemHolder>(MovieItemDiffCallback()) {
    companion object {
        private const val FAVORITE = 1
        private const val NO_FAVORITE = 0
        private const val DEFAULT_RATING = "0.0"
        private const val DEFAULT_SIZE = 0
    }


    var onMovieItemClick: ((Movie) -> Unit)? = null
//    var onShopItemClick:((ShoppingItem)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
//        var layout = R.layout.item_favorite_recycler
//        if (viewType != FAVORITE) {
//            layout = R.layout.item_recycler
//        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieItemHolder(view)
    }

    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {

        if (getItem(position).ratingKinopoisk != null) {
            val rating = getItem(position).ratingKinopoisk.toDouble()
            holder.ratingMovie.text = getItem(position).ratingKinopoisk
            if (rating >= 8) {
                holder.ratingMovie.setBackgroundResource(R.drawable.cercle_green)
            } else if (rating >= 6) {
                holder.ratingMovie.setBackgroundResource(R.drawable.cercle_yellow)
            } else {
                holder.ratingMovie.setBackgroundResource(R.drawable.cercle_red)
            }
        } else {
            holder.ratingMovie.text = DEFAULT_RATING
            holder.ratingMovie.setBackgroundResource(R.drawable.cercle_white)
        }


        holder.nameMovie.text = getItem(position).nameRu
        holder.dateMovie.text = getItem(position).year
        holder.genreMovie.text = getItem(position).genres[DEFAULT_SIZE].toString()

        val movie = getItem(position)
        Glide.with(holder.itemView)
            .load(movie.posterUrl)
            .into(holder.posterMovie)

//        Log.i("MyLog",currentList.size.toString())
        //подгрузка данных
//        if (position == currentList.size-5 ) {
//
//            onEndListener?.invoke()
//        }
//        holder.itemView.setOnLongClickListener {
//            onShopItemLongClick?.invoke(getItem(position))
//            true
//        }
//
        holder.itemView.setOnClickListener {
            onMovieItemClick?.invoke(getItem(position))
        }
    }


//    override fun getItemViewType(position: Int): Int {
//        return if (getItem(position).favourites) {
//            FAVORITE
//        } else {
//            NO_FAVORITE
//        }
//    }
//    interface OnShopItemLongClickListener{
//        fun onShopItemLongClick(shoppingItem: ShoppingItem)
//    }

}