package com.example.marsphotos.test


import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

// Define API service
interface TestPokeApiService {
    @GET("pokemon/{id}") // Fetch details of a specific Pokémon
    suspend fun getPokemonDetails(@Path("id") id: String): JsonElement
}

// Set up Retrofit with Kotlinx Serialization
val json = Json { prettyPrint = true; ignoreUnknownKeys = true } // Pretty print JSON

val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

val apiService = retrofit.create(TestPokeApiService::class.java)

// Run the test
fun main() = runBlocking {
    try {
        val pokemonId = "1" // Change this ID to test different Pokémon
        val response = apiService.getPokemonDetails(pokemonId)

        // Print formatted JSON response
        println(json.encodeToString(JsonElement.serializer(), response))

    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}


