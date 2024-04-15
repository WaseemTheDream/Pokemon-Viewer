package com.example.android.pokemonviewer.ui.pokemonlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.pokemonviewer.data.model.Pokemon

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    onClick: (Pokemon) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable { onClick(pokemon) }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${pokemon.id()}. ${pokemon.name}",
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListItemPreview() {
    PokemonListItem(
        pokemon = Pokemon(
            name = "Pikachu",
            url = "https://pokeapi.co/api/v2/pokemon/2/"),
        onClick = { })
}