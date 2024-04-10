package com.example.android.pokemonviewer.ui.nav

sealed class AppScreen(val route: String) {
    data object PokemonListScreen : AppScreen(AppScreenName.POKEMON_LIST)
    data object PokemonDetailsScreen : AppScreen(
        "${AppScreenName.POKEMON_DETAILS}/{${AppScreenParams.PokemonDetails.POKEMON_ID}}")
}

object AppScreenRoute {
    fun pokemonDetails(pokemonId: String) =
        "${AppScreenName.POKEMON_DETAILS}/$pokemonId"
}

object AppScreenName {
    const val POKEMON_LIST = "pokemon_list"
    const val POKEMON_DETAILS = "pokemon_details"
}

object AppScreenParams {
    object PokemonDetails {
        const val POKEMON_ID = "pokemonId"
    }
}