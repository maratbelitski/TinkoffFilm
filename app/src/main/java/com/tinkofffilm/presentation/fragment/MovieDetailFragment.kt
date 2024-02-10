package com.tinkofffilm.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tinkofffilm.R
import com.tinkofffilm.presentation.detaildisplay.DetailViewModel

class MovieDetailFragment : Fragment() {

    private val myViewModel: DetailViewModel by viewModels()
    private var idKinopoisk: Int = DEFAULT_VALUE
    private lateinit var tvNameMovie: TextView
    private lateinit var tvDateMovie: TextView
    private lateinit var description: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvCountry: TextView
    private lateinit var imagePoster: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var included: View

    companion object {
        private const val ID_KINOPOISK = "id_kinopoisk"
        private const val DEFAULT_VALUE = 0
        fun newInstance(idKinopoisk: Int): MovieDetailFragment {
            val args = Bundle().apply {
                putInt(ID_KINOPOISK, idKinopoisk)
            }
            val fragment = MovieDetailFragment().apply {
                arguments = args
            }
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.idKinopoisk = it.getInt(ID_KINOPOISK)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        myViewModel.loadDetailMovie(idKinopoisk)
        showObservers()
    }

    private fun showObservers() {
        myViewModel.detailMoviesLD.observe(viewLifecycleOwner) {

            tvNameMovie.text = it.nameRu
            tvDateMovie.text = it.year.toString()
            description.text = it.description
            tvGenre.text = it.genres[DEFAULT_VALUE].toString()
            tvCountry.text = it.countries[DEFAULT_VALUE].toString()

            Glide.with(requireActivity())
                .load(it.posterUrl)
                .into(imagePoster)
        }

        myViewModel.progressBarLD.observe(viewLifecycleOwner) {
            if (it == false) {
                progressBar.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.VISIBLE
            }
        }

        myViewModel.noConnectLD.observe(viewLifecycleOwner) {
            if (it == true) {
               included.visibility = View.VISIBLE
            } else {
               included.visibility = View.INVISIBLE
            }
        }
    }

    private fun initViews(view: View) {
        tvNameMovie = view.findViewById(R.id.tvName_movie)
        tvDateMovie = view.findViewById(R.id.tvDate_movie)
        description = view.findViewById(R.id.description)
        tvGenre = view.findViewById(R.id.tvGenre)
        tvCountry = view.findViewById(R.id.tvCountry)
        imagePoster = view.findViewById(R.id.imagePoster)
        progressBar = view.findViewById(R.id.progressBar)
        included = view.findViewById(R.id.included)
    }
}