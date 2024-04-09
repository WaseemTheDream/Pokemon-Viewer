package com.example.android.pokemonviewer.data.network

import com.example.android.pokemonviewer.data.model.PokemonList
import retrofit2.Response

interface PokemonApi {

    companion object {
        const val BASE_URL = "https://pokeapi.co/"
    }

    suspend fun getPokemonList(): Response<PokemonList>
}