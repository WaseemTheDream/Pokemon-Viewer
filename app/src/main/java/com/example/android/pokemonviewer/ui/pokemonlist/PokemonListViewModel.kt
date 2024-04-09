package com.example.android.pokemonviewer.ui.pokemonlist

import androidx.lifecycle.ViewModel
import com.example.android.pokemonviewer.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

}