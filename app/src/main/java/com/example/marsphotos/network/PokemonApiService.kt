package com.example.marsphotos.network

import com.example.marsphotos.model.Pokemon
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import kotlinx.serialization.SerialName
import com.example.marsphotos.model.PokemonDetail
import com.example.marsphotos.model.PokemonSpeciesResponse
import retrofit2.http.Path

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
    @GET("pokemon?limit=300")
    suspend fun getPokemons(): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: String): PokemonDetail

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: String): PokemonSpeciesResponse // Add this line!
}


