package com.tinkofffilm.domain

import androidx.lifecycle.LiveData
import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.ResponseServer
import com.tinkofffilm.data.retrofit.ApiService
import io.reactivex.rxjava3.core.Single

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class LoadPopularMoviesUseCase(private val moviesApiRepository: MoviesRepository) {
    fun loadPopularMovies(page:Int) : Single<ResponseServer>? {
        return  moviesApiRepository.loadPopularMovies(page)
    }
}