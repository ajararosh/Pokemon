package com.example.marsphotos.test

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

// ✅ Define Data Model for Pokémon Details
@Serializable
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,  // Height in decimeters
    val weight: Int,  // Weight in hectograms
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

// ✅ Define Data Model for Pokémon Species (to get description)
@Serializable
data class PokemonSpeciesResponse(
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
)

@Serializable
data class FlavorTextEntry(
    @SerialName("flavor_text") val text: String,
    val language: Language
)

@Serializable
data class Language(
    val name: String
)

// ✅ Define API Service
interface TestPokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: String): PokemonDetail

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: String): PokemonSpeciesResponse
}

// ✅ Retrofit Setup
val json = Json { ignoreUnknownKeys = true }

val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

val apiService = retrofit.create(TestPokeApiService::class.java)

// ✅ Run the test
fun main() = runBlocking {
    try {
        val pokemonId = "1"  // Change to any Pokémon ID (e.g., "150" for Mewtwo)
        val pokemon = apiService.getPokemonDetails(pokemonId)
        val speciesInfo = apiService.getPokemonSpecies(pokemonId)

        // ✅ Extract Pokémon details
        val name = pokemon.name.capitalize()
        val height = pokemon.height / 10.0  // Convert to meters
        val weight = pokemon.weight / 10.0  // Convert to kilograms
        val baseExperience = pokemon.baseExperience
        val typeList = pokemon.types.joinToString { it.type.name.capitalize() }

        // ✅ Get first English description
        val description = speciesInfo.flavorTextEntries
            .firstOrNull { it.language.name == "en" }
            ?.text
            ?.replace("\n", " ")
            ?: "No description available"

        // ✅ Print Results
        println("Pokemon: $name")
        println("Height: $height m")
        println("Weight: $weight kg")
        println("Base Experience: $baseExperience")
        println("Type(s): $typeList")
        println("Description: $description")

    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
