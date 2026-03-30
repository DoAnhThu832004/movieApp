package com.example.movieapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.model.apiService
import com.example.movieapp.view.general.HomeScreen
import com.example.movieapp.viewmodel.TrendingViewModel
import com.example.movieapp.viewmodel.TrendingViewModelFactory

@Composable
fun RecipeApp(
    navController: NavHostController
) {
    val apiKey: String = "0e7d7148db620788481ce0c35b58fefd"
    val trendingViewModel : TrendingViewModel = viewModel(
        factory = TrendingViewModelFactory(apiService)
    )
    val trendState by trendingViewModel.trendState
    val trends = trendState.trendingMovies ?: emptyList()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                trends = trends,
                trendingViewModel = trendingViewModel,
                apiKey = apiKey
            )
        }
    }
}