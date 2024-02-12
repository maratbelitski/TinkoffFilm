package com.tinkofffilm.domain

import com.tinkofffilm.data.pojo.ResponseServer
import com.tinkofffilm.data.retrofit.ApiService
import io.reactivex.rxjava3.core.Single

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class LoadAllMoviesUseCase(private val moviesApiRepository: ApiService) {
    fun loadAllMovies(numberPage: Int): Single<ResponseServer>? {
        return moviesApiRepository.loadMovies(numberPage)
    }
}