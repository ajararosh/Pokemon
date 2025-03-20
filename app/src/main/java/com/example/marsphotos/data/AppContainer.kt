
package com.example.marsphotos.data

import com.example.marsphotos.network.PokeApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val pokemonPhotosRepository: PokemonRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"
//    private val baseUrl = "https://pokeapi.co/api/v2/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val json = Json {
        ignoreUnknownKeys = true // Prevent crashes from unknown JSON keys
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://pokeapi.co/api/v2/")
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }

    /**
     * DI implementation for Mars photos repository
     */

    override val pokemonPhotosRepository: PokemonRepository by lazy {
        NetworkPokemonRepository(retrofitService)
    }

}
