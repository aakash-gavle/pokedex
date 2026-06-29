package com.aakash.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.aakash.android.presentation.viewmodel.PokedexViewModel
import com.aakash.android.di.UseCaseModule
import com.aakash.android.ui.theme.MediaPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appModules = (application as App).appModules
        val repository = appModules.providesPokemonRepository(appModules.provideApiService())
        val getPokemonListUseCase = UseCaseModule.providesGetPokemonListUseCase(repository)
        val getPokemonDetailUseCase = UseCaseModule.providesGetPokemonDetailUseCase(repository)

        val viewModel = PokedexViewModel(getPokemonListUseCase, getPokemonDetailUseCase)

        setContent {
            MediaPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
