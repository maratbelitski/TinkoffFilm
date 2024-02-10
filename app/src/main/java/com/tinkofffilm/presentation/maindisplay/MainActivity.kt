package com.tinkofffilm.presentation.maindisplay

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tinkofffilm.databinding.ActivityMainBinding
import com.tinkofffilm.presentation.maindisplay.adapters.MovieAdapter

class MainActivity : AppCompatActivity() {
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
            myAdapter.submitList(it.items)
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

   private fun doListeners(){
        binding.included.btnReload.setOnClickListener {
            myViewModel.loadMovies()
        }
    }
}