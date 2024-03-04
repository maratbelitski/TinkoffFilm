package com.tinkofffilm.data

import android.app.Application
import com.tinkofffilm.data.pojo.MovieDetail
import com.tinkofffilm.data.pojo.MovieRepo
import com.tinkofffilm.data.pojo.ResponseServer
import com.tinkofffilm.data.retrofit.ApiFactory
import com.tinkofffilm.data.retrofit.ApiService
import com.tinkofffilm.domain.MoviesRepository
import io.reactivex.rxjava3.core.Single

/**
 * @author Belitski Marat
 * @date  10.02.2024
 * @project TinkoffFilm
 */
class MoviesRepositoryImpl(application: Application) : ApiService, MoviesRepository {
    private val dao = MovieDataBase.getDB(application).getDao()
    override fun loadMovies(numberPage: Int): Single<ResponseServer>? {
        return ApiFactory.apiService.loadMovies(numberPage)
    }

    override fun loadDetailMovies(idKinopoisk: Int): Single<MovieDetail>? {
        return ApiFactory.apiService.loadDetailMovies(idKinopoisk)
    }


    override fun insertMovieInDBUseCase(movie: MovieRepo) {
        return dao.insertMovie(movie)
    }

    override fun deleteMovieInDB(id: Int) {
        return dao.removeMovie(id)
    }

    override fun loadPopularMovies(page: Int): Single<ResponseServer>? {
        return ApiFactory.apiService.loadPopularMovies(page)
    }

    override fun loadAllMoviesFromDB(): MutableList<MovieRepo?> {
        return dao.getAllMovies()
    }
}