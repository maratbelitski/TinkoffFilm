package com.tinkofffilm.domain

import com.tinkofffilm.data.pojo.ResponseServer
import io.reactivex.rxjava3.core.Single

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class LoadPopularMoviesUseCase(private val moviesApiRepository: MoviesRepository) {
    fun loadPopularMovies(page: Int): Single<ResponseServer>? {
        return moviesApiRepository.loadPopularMovies(page)
    }
}