package com.tinkofffilm.data.pojo

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.tinkofffilm.data.pojo.Countries
import com.tinkofffilm.data.pojo.Genres

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
    var year : String,

    @SerializedName("posterUrl")
    var posterUrl : String,
)