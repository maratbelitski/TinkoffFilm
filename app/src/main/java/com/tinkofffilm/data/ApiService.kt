package com.tinkofffilm.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
interface ApiService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("films?order=RATING&type=ALL&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000&page=1")
    fun loadMovies(): Single<ResponseServer>?
}