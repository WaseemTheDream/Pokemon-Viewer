package com.example.android.pokemonviewer.ui.pokemondetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.android.pokemonviewer.R
import com.example.android.pokemonviewer.data.ApiResult
import com.example.android.pokemonviewer.data.model.PokemonDetails
import com.example.android.pokemonviewer.data.model.Type
import com.example.android.pokemonviewer.ui.components.ErrorMessage
import com.example.android.pokemonviewer.ui.components.PageLoader
import com.example.android.pokemonviewer.ui.components.TitleBarButton
import com.example.android.pokemonviewer.ui.components.TitleBarText

@Composable
fun PokemonDetailsScreen(
    pokemonId: String,
    pokemonName: String?,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    LaunchedEffect(pokemonId) {
        viewModel.fetchDetails(pokemonId)
    }

    val pokemonState = viewModel.pokemonState.collectAsState()

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
                    text = stringResource(id = R.string.pokemon_details),
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
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        when (result) {
            is ApiResult.Success -> {
                PokemonDetails(
                    pokemon = result.value)
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
}

@Composable
fun PokemonDetails(
    pokemon: PokemonDetails,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 4.dp),
                model = pokemon.sprites.frontDefault,
                contentDescription = null)

            Text(
                text = "${pokemon.id}. ${pokemon.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface)

            PokemonDetailsPrimaryAttributes(
                pokemon = pokemon,
                modifier = Modifier.padding(vertical = 12.dp))

            PokemonDetailsType(types = pokemon.types)

            PokemonDetailsStats(pokemon = pokemon)
        }
    }
}

@Composable
fun PokemonDetailsPrimaryAttributes(
    pokemon: PokemonDetails,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PokemonDetailsItem(
            value = pokemon.height.toString(),
            unit = stringResource(id = R.string.inches),
            label = stringResource(id = R.string.height),
            modifier = Modifier.weight(1f))
        Spacer(
            modifier = Modifier
                .size(1.dp, 60.dp)
                .background(Color.DarkGray))
        PokemonDetailsItem(
            value = pokemon.weight.toString(),
            unit = stringResource(id = R.string.pounds),
            label = stringResource(id = R.string.weight),
            modifier = Modifier.weight(1f))
    }
}

@Composable
fun PokemonDetailsItem(
    value: String,
    unit: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = label)
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .alignBy(LastBaseline))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = unit,
                modifier = Modifier.alignBy(LastBaseline))
        }
    }
}

@Composable
fun PokemonDetailsType(types: List<Type>) {
    Row(
        modifier = Modifier
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for(type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .height(40.dp)
            ) {
                Text(
                    text = type.details.name,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonDetailsStats(
    pokemon: PokemonDetails,
) {
    val maxBaseStat = pokemon.stats.maxOf { it.baseStat }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Base Stats:",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))

        for(stat in pokemon.stats) {
            PokemonDetailsStatItem(
                statName = stat.details.name,
                statValue = stat.baseStat,
                statMaxValue = maxBaseStat,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PokemonDetailsStatItem(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
) {
    val statProgress = statValue / statMaxValue.toFloat()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(statProgress)
                .background(Color.DarkGray)
                .clip(CircleShape)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = (statProgress * statMaxValue).toInt().toString(),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}
