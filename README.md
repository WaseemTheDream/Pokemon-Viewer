# Pokemon-Viewer
A simple Android application that displays a list of Pokemons and allows you to see their details. Pokemon data is backed by the PokeAPI.

## Functionality
The application fetches a list of pokemons from an HTTP JSON endpoint provided by the Poke API.
The pokemons are rendered in a simple list with:
- Pokemon ID
- Pokemon Name

Clicking a pokemon takes us to the Pokemon details page where you can see key attributes related to the pokemon such as image, type, weight, height, etc.

## Libraries Used
<strong>Network Requests: </strong> Retrofit <br>
<strong>Dependency Injection: </strong> Hilt <br>
<strong>Data Layer: </strong> Paging 3 <br>
<strong>UI Architecture: </strong> MVVM + Jetpack Compose <br>
<strong>Image Rendering: </strong> Coil <br>
<strong>Unit Testing: <strong> OkHttp MockWebserver

Retrofit was used to facilitate network requests and ensure type-safety for the responses. Hilt was used as the DI library due to its support for injecting within Android components such as Activities, ViewModels, and Composables. The AndroidX Paging 3 library was used to provide convenient support for pagination of the pokemons list and support error and loading states. Jetpack Compose was used due to its reactive programming model and superior developer experience compared to Android XML Views. MockWebServer was used to create isolated unit tests that don't depend on the network and allow me to provide mocked server responses for testing different conditions.

## App Architecture
The application architecture and code organization is inspired by clean architecture where the functionality is roughly split into Data, Domain, and UI layers with loosely coupled components. This allows the architecture of the application to scale well with additional functionality. <br>
Key Files: <br>
- PokemonApi.kt: Retrofit API for fetching pokemons.
- Pokemon.kt: Data model that represents a pokemon.
- PokemonDetails.kt: Data model that represents the details for a pokemon.
- PokemonRepositoryImpl.kt: Repository that provides pokemons and pokemon details.
- PokemonListScreen.kt: Jetpack Compose screen that renders a list of pokemons
- PokemonListViewModel.kt: ViewModel that backs PokemonListScreen
- PokemonDetailsScreen.kt: Jetpack Compose screen that renders pokemon details
- PokemonDetailsViewModel.kt: ViewModel that backs PokemonDetailsScreen

## Building / Running App
Please use Android Studio to open the root folder of the project. In Android Studio's menu, click Run > Run and select 'app' as the target to build and deploy the app to an emulator / connected Android device.