package com.tinkofffilm.presentation.populare

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.tinkofffilm.R
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.databinding.ActivityPopulareBinding
import com.tinkofffilm.presentation.detaildisplay.DetailActivity
import com.tinkofffilm.presentation.detaildisplay.MovieDetailFragment
import com.tinkofffilm.presentation.maindisplay.MainActivity
import com.tinkofffilm.presentation.maindisplay.adapters.MovieAdapterFavorite

class PopulareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPopulareBinding
    private lateinit var myAdapter: MovieAdapterFavorite
    private var listFromDB = mutableListOf<MovieRepo?>()
    private lateinit var listFromAPI: List<Movie>
    private val myViewModel: PopularViewModel by viewModels()
    private var isEmpty = false



    companion object {
       private const val CURRENT_NUMBER = 1
        private var currentPage = CURRENT_NUMBER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopulareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        showObservers()
        doListeners()
    }

    private fun showObservers() {
        myViewModel.allMoviesLD.observe(this) {

            listFromAPI = it.items

            myViewModel.checkForFavorite(listFromAPI, listFromDB)

            myAdapter.submitList(listFromAPI)
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

        myViewModel.isEmptyLD.observe(this) {
            isEmpty = it
            if (isEmpty) {
                doIfListIsEmpty()
            }
        }

        myViewModel.isFavoriteLD.observe(this) {
            if (it != null){
                listFromDB = it
            }

        }
    }

    private fun doIfListIsEmpty() {
        Toast.makeText(this, R.string.toast_is_empty, Toast.LENGTH_SHORT).show()
        currentPage--
        myViewModel.loadMovies(currentPage)
    }

    private fun doListeners() {
        binding.btnNext.setOnClickListener {
                currentPage++
                myViewModel.loadMovies(currentPage)
        }

        binding.btnPrev.setOnClickListener {
            if (currentPage > CURRENT_NUMBER) {
                currentPage--
                myViewModel.loadMovies(currentPage)
            } else {
                currentPage = CURRENT_NUMBER
            }
        }

        myAdapter.onMovieItemClick = {

            val intent = DetailActivity.launchIntent(this@PopulareActivity, it.kinopoiskId)
            startActivity(intent)
        }

        myAdapter.onStarFavoriteLongClick = {
            if (it.favorite != 1){
                Toast.makeText(this, R.string.toast_favorite, Toast.LENGTH_SHORT).show()
                Log.i("MyLog","Insert 1 - $it + ${it.favorite}")
                myViewModel.insertInDB(it)
            } else {
                myViewModel.removeMovieVM(it.kinopoiskId)
            }
        }

        myAdapter.onMovieItemClick = {

            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    val fragment = MovieDetailFragment.newInstance(it.kinopoiskId)
                    launchFragment(fragment)
                }

                Configuration.ORIENTATION_PORTRAIT -> {
                    val intent = DetailActivity.launchIntent(this@PopulareActivity, it.kinopoiskId)
                    startActivity(intent)
                }
            }
        }
    }


    private fun initViews() {
        myAdapter = MovieAdapterFavorite()
        binding.recycler.adapter = myAdapter
    }

    fun launchIntent(context: Context): Intent {
        return Intent(context, PopulareActivity::class.java)
    }

    private fun launchFragment(fragment: MovieDetailFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragmentDetail, fragment)
            .addToBackStack(null)
            .commit()
    }
}