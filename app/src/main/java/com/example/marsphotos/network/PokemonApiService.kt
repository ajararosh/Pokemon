package com.example.marsphotos.network

import com.example.marsphotos.model.Pokemon
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import kotlinx.serialization.SerialName

@Serializable
data class PokemonResponse(
    @SerialName("results") val results: List<Pokemon> // Keep only needed fields
)
// Optain data from the pokemon
@Serializable
data class Pokemon(
    val name: String,
    val url: String
)

interface PokeApiService {
    @GET("pokemon?limit=300")  // Fetch first 100 Pokémon
    suspend fun getPokemons(): PokemonResponse
}

