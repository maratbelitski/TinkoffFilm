package com.tinkofffilm.presentation.maindisplay

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tinkofffilm.R
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.databinding.ActivityMainBinding
import com.tinkofffilm.presentation.detaildisplay.DetailActivity
import com.tinkofffilm.presentation.favoritedisplay.FavoriteActivity

import com.tinkofffilm.presentation.detaildisplay.MovieDetailFragment
import com.tinkofffilm.presentation.maindisplay.adapters.MovieAdapterFavorite
import com.tinkofffilm.presentation.populare.PopulareActivity

class MainActivity : AppCompatActivity() {
    private val myViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MovieAdapterFavorite
    private var listFromDB = mutableListOf<MovieRepo?>()
    private lateinit var listFromAPI: List<Movie>
    private var isEmpty = false

    companion object {
        private const val IS_ONE = 1
        private var currentPage = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        showObservers()
        doListeners()
    }

    private fun initViews() {
        myAdapter = MovieAdapterFavorite()
        binding.recycler.adapter = myAdapter
    }

    private fun showObservers() {

        myViewModel.isEmptyLD.observe(this) {
            isEmpty = it
            if (isEmpty) {
                doIfListIsEmpty()
            }
        }

        if (!isEmpty) {
            myViewModel.allMoviesFromApiLD.observe(this) {

               listFromAPI = it.items

               myViewModel.checkForFavorite(listFromAPI, listFromDB)

                myAdapter.submitList(listFromAPI)
            }
        }


        myViewModel.progressBarLD.observe(this) {
            if (it == false) {
                binding.progressBar.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }


        myViewModel.noConnectLD.observe(this) {
            if (it == true) {
                binding.included.layoutNoConnect.visibility = View.VISIBLE
            } else {
                binding.included.layoutNoConnect.visibility = View.INVISIBLE
            }
        }

        myViewModel.isFavoriteLD.observe(this) {
            if (it != null){
                listFromDB = it
            }
        }
    }


    private fun doListeners() {
        binding.included.btnReload.setOnClickListener {
            myViewModel.loadMoviesFromAPI(currentPage)
        }

        binding.btnFavorite.setOnClickListener {
            startActivity(FavoriteActivity().launchIntent(this))
        }

        binding.btnPopular.setOnClickListener {
            startActivity(PopulareActivity().launchIntent(this))
        }

        binding.btnNext.setOnClickListener {
            currentPage++
            myViewModel.loadMoviesFromAPI(currentPage)
        }

        binding.btnPrev.setOnClickListener {
            if (currentPage > IS_ONE) {
                currentPage--
                myViewModel.loadMoviesFromAPI(currentPage)
            } else {
                currentPage = IS_ONE
            }
        }

        myAdapter.onMovieItemClick = {

            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    val fragment = MovieDetailFragment.newInstance(it.kinopoiskId)

                    launchFragment(fragment)
                }

                Configuration.ORIENTATION_PORTRAIT -> {
                    val intent = DetailActivity.launchIntent(this@MainActivity, it.kinopoiskId)
                    startActivity(intent)
                }
            }
        }

        myAdapter.onStarFavoriteLongClick = {
            if (it.favorite != IS_ONE){
                Toast.makeText(this, R.string.toast_favorite, Toast.LENGTH_SHORT).show()
                myViewModel.insertInDB(it)
            } else {
                myViewModel.removeMovieVM(it.kinopoiskId)
            }
        }
    }

    private fun doIfListIsEmpty() {
        Toast.makeText(this, R.string.toast_is_empty, Toast.LENGTH_SHORT).show()
        currentPage--
        myViewModel.loadMoviesFromAPI(currentPage)
    }

    private fun launchFragment(fragment: MovieDetailFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragmentDetail, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()

    }
}