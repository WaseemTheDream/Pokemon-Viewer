package com.example.android.pokemonviewer.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,
) {

    fun id(): String = url
        .substringAfter(ID_PREFIX)
        .substringBefore(ID_SUFFIX)

    companion object {
        const val ID_PREFIX = "https://pokeapi.co/api/v2/pokemon/"
        const val ID_SUFFIX = "/"
    }
}

