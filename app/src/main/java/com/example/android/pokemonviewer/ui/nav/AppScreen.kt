package com.example.android.pokemonviewer.ui.nav

sealed class AppScreen(val route: String) {
    object PokemonListScreen : AppScreen(AppScreenName.POKEMON_LIST)
    object PokemonDetailsScreen : AppScreen(AppScreenName.POKEMON_DETAILS)
}

object AppScreenName {
    const val POKEMON_LIST = "pokemon_list"
    const val POKEMON_DETAILS = "pokemon_details"
}