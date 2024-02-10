package com.tinkofffilm.presentation.detaildisplay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tinkofffilm.R
import com.tinkofffilm.databinding.ActivityDetailBinding
import com.tinkofffilm.presentation.fragment.MovieDetailFragment


class DetailActivity : AppCompatActivity() {
    private val myViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private var idKinopoisk = DEFAULT_VALUE

    companion object {
        const val ID_KINOPOISK = "id_kinopoisk"
        const val ID_DEFAULT = -1
        private const val DEFAULT_VALUE = 0

        fun launchIntent(context: Context, idKinopoisk: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ID_KINOPOISK, idKinopoisk)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idKinopoisk = parseIntent()

       // showObservers()


        val fragment = MovieDetailFragment.newInstance(idKinopoisk)
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragmentDetail, fragment)
            .commit()
    }

    private fun parseIntent(): Int {
        return intent.getIntExtra(ID_KINOPOISK, ID_DEFAULT)
    }

//    private fun showObservers() {
//        myViewModel.detailMoviesLD.observe(this) {
//            with(binding) {
//                tvNameMovie.text = it.nameRu
//                tvDateMovie.text = it.year.toString()
//                description.text = it.description
//                tvGenre.text = it.genres[DEFAULT_VALUE].toString()
//                tvCountry.text = it.countries[DEFAULT_VALUE].toString()
//
//                Glide.with(baseContext)
//                    .load(it.posterUrl)
//                    .into(imagePoster)
//            }
//        }
//
//        myViewModel.progressBarLD.observe(this) {
//            if (it == false) {
//                binding.progressBar.visibility = View.INVISIBLE
//            } else {
//                binding.progressBar.visibility = View.VISIBLE
//            }
//        }
//
//        myViewModel.noConnectLD.observe(this) {
//            if (it == true) {
//                binding.included.layoutNoConnect.visibility = View.VISIBLE
//            } else {
//                binding.included.layoutNoConnect.visibility = View.INVISIBLE
//            }
//        }
//    }
}