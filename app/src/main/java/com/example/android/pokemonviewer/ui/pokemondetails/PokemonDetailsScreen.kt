package com.example.android.pokemonviewer.ui.pokemondetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.pokemonviewer.ui.components.TitleBarButton
import com.example.android.pokemonviewer.ui.components.TitleBarText

@Composable
fun PokemonDetailsScreen(
    pokemonId: String,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleBarButton(
                    imageVector = Icons.Default.ArrowBack,
                    onClick = { navigateBack() })
                TitleBarText(
                    text = "Pokemon Name",
                    modifier = Modifier.weight(1f))
            }
        }
    ) { paddingValues ->
        PokemonDetailsScreenContent(
            pokemonId = pokemonId,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun PokemonDetailsScreenContent(
    pokemonId: String,
    viewModel: PokemonDetailsViewModel,
    modifier: Modifier = Modifier,
) {
    Text(
        text = pokemonId,
        modifier = modifier)
}