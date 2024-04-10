package com.example.android.pokemonviewer.data.model

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val details: TypeDetails,
)
