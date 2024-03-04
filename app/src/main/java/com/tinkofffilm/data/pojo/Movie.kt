package com.tinkofffilm.data.pojo

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */

data class Movie(

    @SerializedName("kinopoiskId")
    var kinopoiskId: Int = 0,

    @SerializedName("nameRu")
    var nameRu: String = "Неизвестно",

    @Ignore
    @SerializedName("countries")
    var countries: List<Countries>,

    @Ignore
    @SerializedName("genres")
    var genres: List<Genres>,

    @SerializedName("ratingImdb")
    var ratingImdb: String = "",

    @SerializedName("ratingKinopoisk")
    var ratingKinopoisk: String = "",

    @SerializedName("year")
    var year: String = "Неизвестно",

    @SerializedName("posterUrl")
    var posterUrl: String = "Нет",

    var favorite: Int = 0
)
