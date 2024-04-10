package com.example.android.pokemonviewer.ui.pokemondetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.pokemonviewer.R
import com.example.android.pokemonviewer.data.ApiResult
import com.example.android.pokemonviewer.data.model.PokemonDetails
import com.example.android.pokemonviewer.ui.components.ErrorMessage
import com.example.android.pokemonviewer.ui.components.PageLoader
import com.example.android.pokemonviewer.ui.components.TitleBarButton
import com.example.android.pokemonviewer.ui.components.TitleBarText

@Composable
fun PokemonDetailsScreen(
    pokemonId: String,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    LaunchedEffect(pokemonId) {
        viewModel.fetchDetails(pokemonId)
    }

    val pokemonState = viewModel.pokemonState.collectAsState()
    val pokemonName = when (pokemonState.value) {
        is ApiResult.Success -> {
            (pokemonState.value as ApiResult.Success<PokemonDetails>).value.name
        }
        else -> {
            ""
        }
    }

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
                    text = pokemonName,
                    modifier = Modifier.weight(1f))
            }
        }
    ) { paddingValues ->
        PokemonDetailsScreenContent(
            pokemonState.value,
            modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun PokemonDetailsScreenContent(
    result: ApiResult<PokemonDetails>,
    modifier: Modifier = Modifier,
) {
    when (result) {
        is ApiResult.Success -> {
            Text(
                text = result.value.name,
                modifier = modifier)
        }
        is ApiResult.Failure -> {
            val errorMessage = result.message ?: stringResource(id = R.string.unknown_error)
            ErrorMessage(
                message = errorMessage)
        }
        is ApiResult.Loading -> {
            PageLoader(modifier = Modifier.fillMaxSize())
        }
    }
}