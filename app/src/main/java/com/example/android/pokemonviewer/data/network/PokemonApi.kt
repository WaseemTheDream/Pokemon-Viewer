package com.example.android.pokemonviewer.data.network

import com.example.android.pokemonviewer.data.model.PokemonList
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApi {

    companion object {
        const val BASE_URL = "https://pokeapi.co/"
    }

    @GET("api/v2/pokemon")
    suspend fun getPokemonList(): Response<PokemonList>
}