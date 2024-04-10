package com.example.android.pokemonviewer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.pokemonviewer.core.app.Constants
import com.example.android.pokemonviewer.data.ApiResult
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.data.model.PokemonDetails
import com.example.android.pokemonviewer.data.network.PokemonApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val networkApi: PokemonApi
) : PokemonRepository {

    override fun getPokemons(): Flow<PagingData<Pokemon>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.Pager.PAGE_SIZE,
                prefetchDistance = Constants.Pager.PREFETCH_DISTANCE),
            pagingSourceFactory = {
                PokemonPagingSource(networkApi)
            }
        ).flow

    override fun getDetails(id: String): Flow<ApiResult<PokemonDetails>> = flow {
        try {
            val response = networkApi.getPokemonDetails(id)
            val pokemon: PokemonDetails? = response.body()

            if (!response.isSuccessful || pokemon == null) {
                emit(ApiResult.Failure(response.errorBody()?.toString(), HttpException(response)))
                return@flow
            }

            emit(ApiResult.Success(pokemon))
        } catch (exception: Exception) {
            emit(ApiResult.Failure(exception.localizedMessage, exception))
        }
    }
}