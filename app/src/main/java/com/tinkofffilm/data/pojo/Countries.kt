package com.tinkofffilm.data.pojo

import com.google.gson.annotations.SerializedName

/**
 * @author Belitski Marat
 * @date  09.02.2024
 * @project TinkoffFilm
 */
data class Countries (
    @SerializedName("country") var country : String
) {
    override fun toString(): String {
        return "Страна: $country"
    }
}