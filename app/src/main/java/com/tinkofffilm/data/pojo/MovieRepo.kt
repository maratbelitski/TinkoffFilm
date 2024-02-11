package com.tinkofffilm.data.pojo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Belitski Marat
 * @date  11.02.2024
 * @project TinkoffFilm
 */

@Entity("MovieTable")
data class MovieRepo(

    @PrimaryKey
    var kinopoiskId: Int = 0,
    var nameRu: String? = "",
    var genres : String? = "",
    var year: String? = "",
    var countries: String? = "",
    var ratingKinopoisk: String? = "",
    var posterUrl: String? = "",
    var favorite: Int = 0
)