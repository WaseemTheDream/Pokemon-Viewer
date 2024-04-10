package com.example.android.pokemonviewer.data.network

import com.example.android.pokemonviewer.core.app.Constants
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.data.model.PokemonDetails
import com.example.android.pokemonviewer.data.model.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    companion object {
        const val BASE_URL = "https://pokeapi.co/"
    }

    @GET("api/v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = Constants.Pager.INITIAL_OFFSET,
        @Query("limit") limit: Int = Constants.Pager.PAGE_SIZE,
    ): Response<PokemonList>

    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: String
    ): Response<PokemonDetails>
}