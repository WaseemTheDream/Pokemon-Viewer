package com.example.android.pokemonviewer.ui.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.pokemonviewer.R
import com.example.android.pokemonviewer.ui.components.ErrorMessage
import com.example.android.pokemonviewer.ui.components.LoadingNextPageItem
import com.example.android.pokemonviewer.ui.components.PageLoader
import com.example.android.pokemonviewer.ui.components.TitleBarButton
import com.example.android.pokemonviewer.ui.components.TitleBarText

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel()
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
                TitleBarText(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.weight(1f))
                TitleBarButton(
                    imageVector = Icons.Default.Search,
                    onClick = { /*TODO*/ })
            }
        }
    ) { paddingValues ->
        PokemonListScreenContent(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues))
    }
}

@Composable
fun PokemonListScreenContent(
    viewModel: PokemonListViewModel,
    modifier: Modifier = Modifier,
) {
    val pokemonPagingItems = viewModel.pokemonsState.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier,
    ) {
        items(pokemonPagingItems.itemCount) {index ->
            PokemonListItem(
                pokemon = pokemonPagingItems[index]!!,
                onClick = { /*TODO*/ }
            )
        }

        pokemonPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val loadState = pokemonPagingItems.loadState.refresh as LoadState.Error
                    item {
                        val errorMessage =
                            loadState.error.localizedMessage ?:
                            stringResource(id = R.string.unknown_error)

                        ErrorMessage(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(20.dp),
                            message = errorMessage,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem() }
                }

                loadState.append is LoadState.Error -> {
                    val loadState = pokemonPagingItems.loadState.append as LoadState.Error
                    item {
                        val errorMessage =
                            loadState.error.localizedMessage ?:
                            stringResource(id = R.string.unknown_error)
                        ErrorMessage(
                            modifier = Modifier.padding(20.dp),
                            message = errorMessage,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}