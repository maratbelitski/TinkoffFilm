package com.tinkofffilm.data.pojo

import com.google.gson.annotations.SerializedName
import com.tinkofffilm.data.pojo.Movie

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
class ResponseServer (
    @SerializedName("items")
    var items : List<Movie>
)