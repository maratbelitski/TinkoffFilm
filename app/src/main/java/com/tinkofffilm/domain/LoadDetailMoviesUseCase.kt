package com.tinkofffilm.domain

import com.tinkofffilm.data.pojo.MovieDetail
import com.tinkofffilm.data.retrofit.ApiService
import io.reactivex.rxjava3.core.Single

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class LoadDetailMoviesUseCase(private val moviesApiRepository: ApiService) {
    fun loadAllMovies(idKinopoisk: Int): Single<MovieDetail>? {
        return moviesApiRepository.loadDetailMovies(idKinopoisk)
    }
}