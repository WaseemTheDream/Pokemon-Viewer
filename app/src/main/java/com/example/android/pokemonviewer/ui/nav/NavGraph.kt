package com.example.android.pokemonviewer.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.pokemonviewer.ui.pokemonlist.PokemonListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.PokemonListScreen.route
    ) {
        composable(route = AppScreen.PokemonListScreen.route) {
            PokemonListScreen()
        }
        composable(route = AppScreen.PokemonDetailsScreen.route) {

        }
    }
}