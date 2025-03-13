
package com.example.marsphotos.data

import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.network.PokeApiService

/**
 * Repository that fetch mars photos list from marsApi.
 */
interface PokemonRepository {
    suspend fun getPokemonList(): List<Pokemon>
}

class NetworkPokemonRepository(
    private val pokeApiService: PokeApiService
) : PokemonRepository {
    override suspend fun getPokemonList(): List<Pokemon> = pokeApiService.getPokemons().results
}

