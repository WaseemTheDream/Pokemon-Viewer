package com.example.android.pokemonviewer.data.repository

import androidx.paging.PagingData
import com.example.android.pokemonviewer.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemons(): Flow<PagingData<Pokemon>>
}