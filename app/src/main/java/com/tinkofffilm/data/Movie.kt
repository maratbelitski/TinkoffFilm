package com.tinkofffilm.data

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
data class Movie (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @SerializedName("kinopoiskId")
    var kinopoiskId : Int,

    @SerializedName("nameRu")
    var nameRu : String,

    @SerializedName("countries")
    var countries : List<Countries>,

    @SerializedName("genres")
    var genres : List<Genres>,

    @SerializedName("ratingImdb")
    var ratingImdb : String,

    @SerializedName("ratingKinopoisk")
    var ratingKinopoisk : String,

    @SerializedName("year")
    var year : Int,

    @SerializedName("posterUrl")
    var posterUrl : String,
)