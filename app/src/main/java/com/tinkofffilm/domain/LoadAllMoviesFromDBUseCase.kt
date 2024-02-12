package com.tinkofffilm.domain

import com.tinkofffilm.data.pojo.MovieRepo

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class LoadAllMoviesFromDBUseCase(private val moviesApiRepository: MoviesRepository) {
    fun loadAllMoviesFromDB(): MutableList<MovieRepo> {
        return moviesApiRepository.loadAllMoviesFromDB()
    }
}