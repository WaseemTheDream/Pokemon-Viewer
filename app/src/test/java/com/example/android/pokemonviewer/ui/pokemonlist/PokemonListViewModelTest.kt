package com.example.android.pokemonviewer.ui.pokemonlist

import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.example.android.pokemonviewer.data.network.PokemonApi
import com.example.android.pokemonviewer.data.repository.PokemonRepositoryImpl
import com.example.android.pokemonviewer.util.TestFileReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class PokemonListViewModelTest {

    private lateinit var viewModel: PokemonListViewModel
    private lateinit var mockWebServer: MockWebServer

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val pokemonApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)

        viewModel = PokemonListViewModel(PokemonRepositoryImpl(pokemonApi))
    }

    @Test
    fun `pokemons are correctly loaded`() = runTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestFileReader("sample_pokemons.json").content)

        mockWebServer.enqueue(response)

        val result = viewModel.pokemonsState.asSnapshot()
        assertEquals(20, result.size)
        assertEquals("bulbasaur", result[0].name)
    }

    @Test
    fun `pagination works as expected`() = runTest {
        val response1 = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestFileReader("sample_pokemons.json").content)

        val response2 = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestFileReader("sample_pokemons_2.json").content)

        mockWebServer.enqueue(response1)
        mockWebServer.enqueue(response2)

        val result = viewModel.pokemonsState.asSnapshot {
            scrollTo(20)  // Trigger pagination request
        }

        assertEquals(result.size, 40)
    }

    @Test
    fun `http errors are handled`() = runTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            .setBody("")

        mockWebServer.enqueue(response)

        val result = viewModel.pokemonsState.asSnapshot(onError = { ErrorRecovery.RETURN_CURRENT_SNAPSHOT })

        assertTrue(result.isEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}