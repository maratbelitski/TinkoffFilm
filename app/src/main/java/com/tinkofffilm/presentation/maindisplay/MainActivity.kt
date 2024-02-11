package com.tinkofffilm.presentation.maindisplay

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tinkofffilm.R
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

    companion object {
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
        myViewModel.allMoviesFromApiLD.observe(this) {
            myAdapter.submitList(it?.items)
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

        binding.btnNext?.setOnClickListener {
            currentPage++
            myViewModel.loadMoviesFromAPI(currentPage)
        }

        binding.btnPrev?.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                myViewModel.loadMoviesFromAPI(currentPage)
            } else {
                currentPage = 1
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
            myViewModel.insertInDB(it)
            Toast.makeText(this, "Фильм добавлен в избранное", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchFragment(fragment: MovieDetailFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragmentDetail, fragment)
            .addToBackStack(null)
            .commit()
    }
}