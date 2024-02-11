package com.tinkofffilm.presentation.favoritedisplay

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tinkofffilm.databinding.ActivityFavoriteBinding
import com.tinkofffilm.presentation.detaildisplay.DetailActivity
import com.tinkofffilm.presentation.favoritedisplay.adapters.AdapterMovieRepo
import com.tinkofffilm.presentation.fragment.MovieDetailFragment
import com.tinkofffilm.presentation.maindisplay.adapters.MovieAdapterFavorite
import com.tinkofffilm.presentation.populare.PopulareActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var myAdapter: AdapterMovieRepo
    private val myViewModel: FavoriteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        showObservers()
        doListeners()
        doSwipe()
    }

    private fun showObservers() {
        myViewModel.isFavoriteLD.observe(this) {
            myAdapter.submitList(it)
        }
    }

    private fun doListeners() {
        myAdapter.onStarFavoriteLongClick = {
            myViewModel.removeMovieVM(it.kinopoiskId)
        }

        myAdapter.onMovieItemClick = {
            val intent = DetailActivity.launchIntent(this@FavoriteActivity, it.kinopoiskId)
            startActivity(intent)
        }
    }

    private fun doSwipe() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movie = myAdapter.currentList[viewHolder.adapterPosition]
                myViewModel.removeMovieVM(movie.kinopoiskId)
                myViewModel.loadFavoriteMovie()
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {

                val movie = myAdapter.currentList[viewHolder.adapterPosition]
                if (movie.nameRu == "") return 0
                return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recycler)
    }

    private fun initViews() {
        myAdapter = AdapterMovieRepo()
        binding.recycler.adapter = myAdapter
    }

    fun launchIntent(context: Context): Intent {
        return Intent(context, FavoriteActivity::class.java)
    }
}


