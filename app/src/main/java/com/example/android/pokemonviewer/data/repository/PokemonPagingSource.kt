package com.example.android.pokemonviewer.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.pokemonviewer.core.app.Constants
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.data.network.PokemonApi
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource(
    private val networkApi: PokemonApi
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val offset = params.key ?: Constants.Pager.INITIAL_OFFSET
            val response = networkApi.getPokemonList(offset)
            val pokemonResponse = response.body()

            if (!response.isSuccessful || pokemonResponse == null) {
                return LoadResult.Error(HttpException(response))
            }

            val prevKey =
                if (offset == Constants.Pager.INITIAL_OFFSET) null
                else offset - Constants.Pager.PAGE_SIZE
            val nextKey =
                if (pokemonResponse.results.isEmpty()) null
                else offset + Constants.Pager.PAGE_SIZE

            LoadResult.Page(
                data = pokemonResponse.results,
                prevKey = prevKey,
                nextKey = nextKey)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}