package com.example.android.pokemonviewer.ui.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.pokemonviewer.R
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.ui.components.ErrorMessage
import com.example.android.pokemonviewer.ui.components.LoadingNextPageItem
import com.example.android.pokemonviewer.ui.components.PageLoader
import com.example.android.pokemonviewer.ui.components.SearchBar
import com.example.android.pokemonviewer.ui.components.SearchBarState
import com.example.android.pokemonviewer.ui.components.TitleBarButton
import com.example.android.pokemonviewer.ui.components.TitleBarText

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    navigateToDetailsScreen: (Pokemon) -> Unit,
    navigateToSearchResults: (String) -> Unit,
) {
    val searchBarState = rememberSaveable { mutableStateOf(SearchBarState.CLOSED) }
    val onSearchOpenClicked: () -> Unit = {
        searchBarState.value = SearchBarState.OPEN
    }
    val onSearchCloseClicked: () -> Unit = {
        searchBarState.value = SearchBarState.CLOSED
    }
    val onSearchTextChanged: (String) -> Unit = {
        viewModel.searchPokemons(it)
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
                if (searchBarState.value == SearchBarState.OPEN) {
                    SearchBar(
                        onSearchCloseClicked = onSearchCloseClicked,
                        onSearchClicked = navigateToSearchResults,
                        onSearchTextChanged = onSearchTextChanged)
                }  else {
                    DefaultAppBar(onSearchClicked = onSearchOpenClicked)
                }
            }
        }
    ) { paddingValues ->
        PokemonListScreenContent(
            viewModel = viewModel,
            navigateToDetailsScreen = navigateToDetailsScreen,
            searchBarState = searchBarState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues))
    }
}

@Composable
fun RowScope.DefaultAppBar(
    onSearchClicked: () -> Unit,
) {
    TitleBarText(
        text = stringResource(id = R.string.app_name),
        modifier = Modifier.weight(1f))
    TitleBarButton(
        imageVector = Icons.Default.Search,
        onClick = onSearchClicked)
}

@Composable
fun PokemonListScreenContent(
    viewModel: PokemonListViewModel,
    navigateToDetailsScreen: (Pokemon) -> Unit,
    searchBarState: MutableState<SearchBarState>,
    modifier: Modifier = Modifier,
) {
    val pokemonPagingItems =
        if (searchBarState.value == SearchBarState.CLOSED) {
            viewModel.pokemonsState.collectAsLazyPagingItems()
        } else {
            viewModel.searchResultsState.collectAsLazyPagingItems()
        }

    LazyColumn(
        modifier = modifier,
    ) {
        items(pokemonPagingItems.itemCount) {index ->
            val pokemon = pokemonPagingItems[index]!!
            PokemonListItem(
                pokemon = pokemon,
                onClick = { navigateToDetailsScreen(pokemon) }
            )
        }

        pokemonPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading &&
                        searchBarState.value == SearchBarState.CLOSED -> {
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

                loadState.append is LoadState.Loading &&
                        searchBarState.value == SearchBarState.CLOSED -> {
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