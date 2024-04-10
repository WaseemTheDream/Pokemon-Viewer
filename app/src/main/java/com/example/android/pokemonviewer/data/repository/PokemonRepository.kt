package com.example.android.pokemonviewer.data.repository

import androidx.paging.PagingData
import com.example.android.pokemonviewer.data.ApiResult
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.data.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemons(): Flow<PagingData<Pokemon>>

    fun getDetails(id: String): Flow<ApiResult<PokemonDetails>>
}