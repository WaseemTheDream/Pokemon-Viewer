package com.example.android.pokemonviewer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.pokemonviewer.core.app.Constants
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.data.network.PokemonApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val networkApi: PokemonApi
) : PokemonRepository {

    override suspend fun getPokemons(): Flow<PagingData<Pokemon>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.Pager.PAGE_SIZE,
                prefetchDistance = Constants.Pager.PREFETCH_DISTANCE),
            pagingSourceFactory = {
                PokemonPagingSource(networkApi)
            }
        ).flow
}