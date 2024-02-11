package com.tinkofffilm.domain

import com.tinkofffilm.data.pojo.MovieRepo

/**
 * @author Belitski Marat
 * @date  11.02.2024
 * @project TinkoffFilm
 */
class DeleteMovieIFromDBUseCase(private val moviesRepository:MoviesRepository) {
    fun deleteMovieInDB(id:Int){
      return moviesRepository.deleteMovieInDB(id)
    }
}