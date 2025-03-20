
package com.example.marsphotos.data

import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.model.PokemonDetail
import com.example.marsphotos.model.PokemonSpeciesResponse
import com.example.marsphotos.test.newui.PokeApiService

/**
 * Repository that fetch mars photos list from marsApi.
 */
interface PokemonRepository {
    suspend fun getPokemonList(): List<Pokemon>
    suspend fun getPokemonDetails(pokemonId: String): PokemonDetail // Add this
    suspend fun getPokemonSpecies(pokemonId: String): PokemonSpeciesResponse // Add this
}

class NetworkPokemonRepository(
    private val pokeApiService: PokeApiService
) : PokemonRepository {
    override suspend fun getPokemonList(): List<Pokemon> = pokeApiService.getPokemons().results
    override suspend fun getPokemonDetails(pokemonId: String): PokemonDetail = pokeApiService.getPokemonDetails(pokemonId) // Implement this
    override suspend fun getPokemonSpecies(pokemonId: String): PokemonSpeciesResponse = pokeApiService.getPokemonSpecies(pokemonId) // Implement this
}

