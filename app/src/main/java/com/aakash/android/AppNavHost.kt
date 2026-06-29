package com.aakash.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aakash.android.presentation.screen.PokemonDetailScreen
import com.aakash.android.presentation.screen.PokemonListScreen
import com.aakash.android.presentation.viewmodel.PokedexViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: PokedexViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "pokemonList",
        modifier = modifier
    ) {
        composable("pokemonList") {
            PokemonListScreen(
                viewModel = viewModel,
                onNavigateToDetails = { name ->
                    navController.navigate("pokemonDetail/$name")
                }
            )
        }
        composable(
            "pokemonDetail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            PokemonDetailScreen(
                name = name,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
