package com.example.android.pokemonviewer.core.di

import com.example.android.pokemonviewer.data.repository.PokemonRepository
import com.example.android.pokemonviewer.data.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonRepository(repositoryImpl: PokemonRepositoryImpl): PokemonRepository
}