package com.tinkofffilm.data.retrofit

import com.tinkofffilm.data.pojo.ResponseServer
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
interface ApiService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("films/collections?type=TOP_POPULAR_MOVIES&page=1")
    fun loadMovies(): Single<ResponseServer>?
}