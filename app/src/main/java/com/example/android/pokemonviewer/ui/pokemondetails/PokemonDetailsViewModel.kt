package com.example.android.pokemonviewer.ui.pokemondetails

import androidx.lifecycle.ViewModel
import com.example.android.pokemonviewer.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

}