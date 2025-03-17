package com.example.marsphotos.model

// PokemonDetail.kt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @SerialName("base_experience") val baseExperience: Int,
    val types: List<PokemonTypeWrapper>,
    val species: PokemonSpeciesInfo
)

@Serializable
data class PokemonTypeWrapper(
    val type: PokemonType
)

@Serializable
data class PokemonType(
    val name: String
)

@Serializable
data class PokemonSpeciesInfo(
    val url: String
)

