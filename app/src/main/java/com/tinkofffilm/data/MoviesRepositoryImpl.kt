package com.tinkofffilm.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.tinkofffilm.data.pojo.Movie
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
class MoviesRepositoryImpl(application: Application) : MoviesRepository,ApiService {
    override fun loadMovies(): Single<ResponseServer>? {
        return ApiFactory.apiService.loadMovies()
    }
}