package com.tinkofffilm.data

import com.google.gson.annotations.SerializedName

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
class ResponseServer (
    @SerializedName("items") var items : List<Movie>
)