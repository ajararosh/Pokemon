package com.example.marsphotos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class defines a Mars photo which includes an ID, and the image URL.
 */
@Serializable
data class Pokemon(
    val name: String,
    @SerialName("url") val url: String
) {
    val id: String
        get() = url.split("/".toRegex()).dropLast(1).last()

    val imageUrl: String
//        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

