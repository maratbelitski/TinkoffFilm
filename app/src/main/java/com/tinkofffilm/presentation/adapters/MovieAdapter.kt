package com.tinkofffilm.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.tinkofffilm.R
import com.tinkofffilm.data.Movie
import com.tinkofffilm.presentation.MovieItemDiffCallback
import com.tinkofffilm.presentation.MovieItemHolder

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
class MovieAdapter : ListAdapter<Movie, MovieItemHolder>(MovieItemDiffCallback()) {
    companion object {
        private const val FAVORITE = 1
        private const val NO_FAVORITE = 0
    }

//    var shopItemList = mutableListOf<ShoppingItem>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

//    var onShopItemLongClick:((ShoppingItem)->Unit)? = null //Пример с лямбдой
//    //var onShopItemClick:OnShopItemLongClickListener? = null
//    var onShopItemClick:((ShoppingItem)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
//        var layout = R.layout.item_favorite_recycler
//        if (viewType != FAVORITE) {
//            layout = R.layout.item_recycler
//        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieItemHolder(view)
    }

//    override fun getItemCount(): Int {
//        return shopItemList.size
//    }

    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {

        //  holder.posterMovie.setImageResource(getItem(position))
        holder.ratingMovie.text = getItem(position).ratingKinopoisk
        holder.nameMovie.text = getItem(position).nameRu
        holder.dateMovie.text = getItem(position).year.toString()

        val movie = getItem(position)
        Glide.with(holder.itemView)
            .load(movie.posterUrl)
            .into(holder.posterMovie)


//        holder.itemView.setOnLongClickListener {
//            onShopItemLongClick?.invoke(getItem(position))
//            true
//        }
//
//        holder.itemView.setOnClickListener {
//            onShopItemClick?.invoke(getItem(position))
//        }
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