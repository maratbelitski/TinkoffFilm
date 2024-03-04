package com.tinkofffilm.domain

import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.data.pojo.ResponseServer
import io.reactivex.rxjava3.core.Single

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
interface MoviesRepository {
    fun insertMovieInDBUseCase(movie: MovieRepo)
    fun deleteMovieInDB(id: Int)
    fun loadPopularMovies(page: Int): Single<ResponseServer>?
    fun loadAllMoviesFromDB(): MutableList<MovieRepo?>
}