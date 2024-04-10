package com.example.android.pokemonviewer.data

sealed class ApiResult<out T> {

    data object Loading : ApiResult<Nothing>()

    data class Success<out R>(val value: R): ApiResult<R>()

    data class Failure(
        val message: String?,
        val throwable: Throwable?
    ): ApiResult<Nothing>()
}