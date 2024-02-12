package com.tinkofffilm.data.pojo

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
data class MovieDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("kinopoiskId")
    var kinopoiskId: Int,

    @SerializedName("nameRu")
    var nameRu: String,

    @SerializedName("posterUrl")
    var posterUrl: String,

    @SerializedName("ratingKinopoisk")
    var ratingKinopoisk: Double,

    @SerializedName("genres")
    var genres: List<Genres>,

    @SerializedName("year")
    var year: Int,

    @SerializedName("description")
    var description: String,

    @SerializedName("countries")
    var countries: List<Countries>,
)