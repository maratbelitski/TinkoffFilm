package com.tinkofffilm.domain

import com.tinkofffilm.data.pojo.Movie
import com.tinkofffilm.data.pojo.MovieRepo

/**
 * @author Belitski Marat
 * @date  11.02.2024
 * @project TinkoffFilm
 */
class InsertMovieInDBUseCase(private val moviesRepository: MoviesRepository) {
    fun insertMovieInDB(movie: MovieRepo){
      return moviesRepository.insertMovieInDBUseCase(movie)
    }
}