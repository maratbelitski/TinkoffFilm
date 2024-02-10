package com.tinkofffilm.presentation.maindisplay

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.tinkofffilm.R
import com.tinkofffilm.databinding.ActivityMainBinding
import com.tinkofffilm.presentation.detaildisplay.DetailActivity
import com.tinkofffilm.presentation.fragment.MovieDetailFragment
import com.tinkofffilm.presentation.maindisplay.adapters.MovieAdapter

class MainActivity : AppCompatActivity(){
    private val myViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        showObservers()
        doListeners()
    }

    private fun initViews() {
        myAdapter = MovieAdapter()
        binding.recycler.adapter = myAdapter
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
        binding.included.btnReload.setOnClickListener {
            myViewModel.loadMovies()
        }


        myAdapter.onMovieItemClick = {

            when(resources.configuration.orientation){
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
    }

    private fun launchFragment(fragment: MovieDetailFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragmentDetail, fragment)
            .addToBackStack(null)
            .commit()
    }
}