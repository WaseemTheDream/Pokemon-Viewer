package com.example.android.pokemonviewer.ui.nav

import com.example.android.pokemonviewer.ui.nav.AppScreenParams.PokemonDetails

sealed class AppScreen(val route: String) {
    data object PokemonListScreen : AppScreen(AppScreenName.POKEMON_LIST)
    data object PokemonDetailsScreen : AppScreen(
        "${AppScreenName.POKEMON_DETAILS}/" +
                "{${PokemonDetails.POKEMON_ID}}" +
                "?${PokemonDetails.POKEMON_NAME}={${PokemonDetails.POKEMON_NAME}}")
}

object AppScreenRoute {
    fun pokemonDetails(pokemonId: String, pokemonName: String) =
        "${AppScreenName.POKEMON_DETAILS}/$pokemonId?${PokemonDetails.POKEMON_NAME}=$pokemonName"
}

object AppScreenName {
    const val POKEMON_LIST = "pokemon_list"
    const val POKEMON_DETAILS = "pokemon_details"
}

object AppScreenParams {
    object PokemonDetails {
        const val POKEMON_ID = "pokemonId"
        const val POKEMON_NAME = "pokemonName"
    }
}