package com.example.android.pokemonviewer.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetails(
    @SerializedName("id")
    val id: Int,

    @SerializedName("sprites")
    val sprites: Sprites,

    @SerializedName("name")
    val name: String,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("types")
    val types: List<Type>,

    @SerializedName("stats")
    val stats: List<Stat>,
)