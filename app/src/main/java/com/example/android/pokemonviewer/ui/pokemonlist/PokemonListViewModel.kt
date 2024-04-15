package com.example.android.pokemonviewer.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    private val _pokemonsState: MutableStateFlow<PagingData<Pokemon>> =
        MutableStateFlow(value = PagingData.empty())
    val pokemonsState: StateFlow<PagingData<Pokemon>> = _pokemonsState

    private val _searchResultsState: MutableStateFlow<PagingData<Pokemon>> =
        MutableStateFlow(value = PagingData.empty())
    val searchResultsState: StateFlow<PagingData<Pokemon>> = _searchResultsState

    private var searchJob: Job? = null

    init {
        fetchPokemons()
    }

    fun searchPokemons(query: String) {
        if (query.isBlank()) {
            searchJob?.cancel()
            searchJob = null
            _searchResultsState.value = PagingData.empty()
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            _pokemonsState.map { pagingData ->
                pagingData.filter { it.name.contains(query) }
            }.collect {
                _searchResultsState.value = it
            }
        }
    }

    private fun fetchPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository
                .getPokemons()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _pokemonsState.value = it
                }
        }
    }
}