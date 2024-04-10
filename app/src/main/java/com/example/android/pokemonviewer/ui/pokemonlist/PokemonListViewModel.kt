package com.example.android.pokemonviewer.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.pokemonviewer.data.model.Pokemon
import com.example.android.pokemonviewer.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    private val _pokemonsState: MutableStateFlow<PagingData<Pokemon>> =
        MutableStateFlow(value = PagingData.empty())
    val pokemonsState: StateFlow<PagingData<Pokemon>> = _pokemonsState

    init {
        fetchPokemons()
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