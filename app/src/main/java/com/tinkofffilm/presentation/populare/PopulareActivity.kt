package com.tinkofffilm.presentation.populare

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.tinkofffilm.R
import com.tinkofffilm.databinding.ActivityPopulareBinding
import com.tinkofffilm.presentation.detaildisplay.DetailActivity
import com.tinkofffilm.presentation.detaildisplay.MovieDetailFragment
import com.tinkofffilm.presentation.maindisplay.adapters.MovieAdapterFavorite

class PopulareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPopulareBinding
    private lateinit var myAdapter: MovieAdapterFavorite
    private val myViewModel: PopularViewModel by viewModels()

    companion object {
        private var currentPage = 1
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
        binding.btnNext?.setOnClickListener {
            currentPage++
            myViewModel.loadMovies(currentPage)
        }

        binding.btnPrev?.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                myViewModel.loadMovies(currentPage)
            } else {
                currentPage = 1
            }
        }

        myAdapter.onMovieItemClick = {

            val intent = DetailActivity.launchIntent(this@PopulareActivity, it.kinopoiskId)
            startActivity(intent)
        }

        myAdapter.onStarFavoriteLongClick = {
            myViewModel.insertInDB(it)
            Toast.makeText(this, "Фильм добавлен в избранное", Toast.LENGTH_SHORT).show()
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