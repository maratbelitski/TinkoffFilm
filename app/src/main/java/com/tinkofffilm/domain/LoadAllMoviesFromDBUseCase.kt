package com.tinkofffilm.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.data.pojo.ResponseServer
import com.tinkofffilm.data.retrofit.ApiService
import io.reactivex.rxjava3.core.Single

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class LoadAllMoviesFromDBUseCase(private val moviesApiRepository: MoviesRepository) {
    fun loadAllMoviesFromDB() : MutableList<MovieRepo> {
        return  moviesApiRepository.loadAllMoviesFromDB()
    }
}