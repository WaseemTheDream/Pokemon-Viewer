package com.example.android.pokemonviewer.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.ui.nav.AppScreenParams.PokemonDetails
import com.example.android.pokemonviewer.ui.pokemondetails.PokemonDetailsScreen
import com.example.android.pokemonviewer.ui.pokemonlist.PokemonListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navigateToDetailsScreen: (Pokemon) -> Unit = {
        navController.navigate(AppScreenRoute.pokemonDetails(it.id(), it.name))
    }
    NavHost(
        navController = navController,
        startDestination = AppScreen.PokemonListScreen.route
    ) {
        composable(route = AppScreen.PokemonListScreen.route) {
            PokemonListScreen(
                navigateToDetailsScreen = navigateToDetailsScreen
            )
        }
        composable(
            route = AppScreen.PokemonDetailsScreen.route,
            arguments = listOf(
                navArgument(PokemonDetails.POKEMON_ID) { type = NavType.StringType },
                navArgument(PokemonDetails.POKEMON_NAME) { type = NavType.StringType },
            )
        ) {
            val pokemonId =
                it.arguments?.getString(PokemonDetails.POKEMON_ID) ?:
                throw IllegalArgumentException("Missing required pokemon id")
            val pokemonName =
                it.arguments?.getString(PokemonDetails.POKEMON_NAME)
            val navigateBack: () -> Unit = {
                navController.popBackStack()
            }
            PokemonDetailsScreen(
                pokemonId = pokemonId,
                pokemonName = pokemonName,
                navigateBack = navigateBack)
        }
    }
}