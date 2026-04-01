package com.example.movieapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.model.apiService
import com.example.movieapp.view.general.HomeScreen
import com.example.movieapp.view.trending.DetailTrendingScreen
import com.example.movieapp.viewmodel.NowPLayingViewModelFactory
import com.example.movieapp.viewmodel.NowPlayingViewModel
import com.example.movieapp.viewmodel.PopularViewModel
import com.example.movieapp.viewmodel.PopularViewModelFactory
import com.example.movieapp.viewmodel.TopRatedViewModel
import com.example.movieapp.viewmodel.TopRatedViewModelFactory
import com.example.movieapp.viewmodel.TrendingViewModel
import com.example.movieapp.viewmodel.TrendingViewModelFactory
import com.example.movieapp.viewmodel.UpcomingViewModel
import com.example.movieapp.viewmodel.UpcomingViewModelFactory

@Composable
fun RecipeApp(
    navController: NavHostController
) {
    val apiKey: String = "0e7d7148db620788481ce0c35b58fefd"
    val trendingViewModel : TrendingViewModel = viewModel(
        factory = TrendingViewModelFactory(apiService)
    )
    val nowPlayingViewModel: NowPlayingViewModel = viewModel(
        factory = NowPLayingViewModelFactory(apiService)
    )
    val popularViewModel: PopularViewModel = viewModel(
        factory = PopularViewModelFactory(apiService)
    )
    val topRatedViewModel: TopRatedViewModel = viewModel(
        factory = TopRatedViewModelFactory(apiService)
    )
    val upComingViewModel: UpcomingViewModel = viewModel(
        factory = UpcomingViewModelFactory(apiService)
    )
    val trendState by trendingViewModel.trendState
    val trends = trendState.trendingMovies ?: emptyList()
    val nowPlayingState by nowPlayingViewModel.nowPlayingState
    val nowPlayings = nowPlayingState.nowPlaying ?: emptyList()
    val popularState by popularViewModel.popularState
    val populars = popularState.popular ?: emptyList()
    val topRatedState by topRatedViewModel.topRatedState
    val topRateds = topRatedState.topRated ?: emptyList()
    val upComingState by upComingViewModel.upComingState
    val upcomings = upComingState.upcoming ?: emptyList()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                trends = trends,
                nowPlayings = nowPlayings,
                populars = populars,
                topRateds = topRateds,
                upcomings = upcomings,
                trendingViewModel = trendingViewModel,
                nowPlayingViewModel = nowPlayingViewModel,
                popularViewModel = popularViewModel,
                topRatedViewModel = topRatedViewModel,
                upComingViewModel = upComingViewModel,
                apiKey = apiKey,
                onDetailClick = { trendId ->
                    navController.navigate(Screen.DetailTrendScreen.createRoute(trendId.toInt()))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.DetailTrendScreen.route) {
            val trendId = it.arguments?.getString("trendId")
            val trend = trends.find { it.id.toString() == trendId }
            if (trend != null) {
                DetailTrendingScreen(
                    trend = trend,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}