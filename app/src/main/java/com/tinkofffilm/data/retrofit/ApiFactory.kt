package com.tinkofffilm.data.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
object ApiFactory {
    private const val BASE_URI = "https://kinopoiskapiunofficial.tech/api/v2.2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URI)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}