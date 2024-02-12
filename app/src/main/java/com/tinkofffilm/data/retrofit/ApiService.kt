package com.tinkofffilm.data.retrofit

import com.tinkofffilm.data.pojo.MovieDetail
import com.tinkofffilm.data.pojo.ResponseServer
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
interface ApiService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/collections?type=TOP_POPULAR_ALL")
    fun loadMovies(@Query("page") numberPage: Int): Single<ResponseServer>?

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/{idKinopoisk}")
    fun loadDetailMovies(@Path("idKinopoisk") idKinopoisk: Int): Single<MovieDetail>?

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/collections?type=TOP_250_MOVIES")
    fun loadPopularMovies(@Query("page") numberPage: Int): Single<ResponseServer>?
}