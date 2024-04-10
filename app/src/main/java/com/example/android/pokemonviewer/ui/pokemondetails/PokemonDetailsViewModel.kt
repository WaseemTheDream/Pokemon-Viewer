package com.example.android.pokemonviewer.ui.pokemondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.pokemonviewer.data.ApiResult
import com.example.android.pokemonviewer.data.model.PokemonDetails
import com.example.android.pokemonviewer.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    private val _pokemonState: MutableStateFlow<ApiResult<PokemonDetails>> =
        MutableStateFlow(value = ApiResult.Loading)
    val pokemonState: StateFlow<ApiResult<PokemonDetails>> = _pokemonState

    fun fetchDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository
                .getDetails(id)
                .distinctUntilChanged()
                .collect {
                    _pokemonState.value = it
                }
        }
    }
}