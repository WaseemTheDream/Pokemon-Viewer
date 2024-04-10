package com.example.android.pokemonviewer.data.model

import com.google.gson.annotations.SerializedName

data class TypeDetails(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,
)
