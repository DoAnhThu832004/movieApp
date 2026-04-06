package com.example.movieapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.model.apiService
import com.example.movieapp.view.charactor.DetailCharactorScreen
import com.example.movieapp.view.collection.DetailCollectionScreen
import com.example.movieapp.view.general.HomeScreen
import com.example.movieapp.view.movie.DetailMovieScreen
import com.example.movieapp.view.trending.DetailTrendingScreen
import com.example.movieapp.viewmodel.CollectionViewModel
import com.example.movieapp.viewmodel.CollectionViewModelFactory
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.viewmodel.MovieViewModelFactory
import com.example.movieapp.viewmodel.NowPLayingViewModelFactory
import com.example.movieapp.viewmodel.NowPlayingViewModel
import com.example.movieapp.viewmodel.PersonDetailViewModel
import com.example.movieapp.viewmodel.PersonDetailViewModelFactory
import com.example.movieapp.viewmodel.PersonViewModel
import com.example.movieapp.viewmodel.PersonViewModelFactory
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
    val apiKey
    = "0e7d7148db620788481ce0c35b58fefd"
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
    val personViewModel: PersonViewModel = viewModel(
        factory = PersonViewModelFactory(apiService)
    )
    val personDetailViewModel: PersonDetailViewModel = viewModel(
        factory = PersonDetailViewModelFactory(apiService)
    )
    val collectionViewModel: CollectionViewModel = viewModel(
        factory = CollectionViewModelFactory(apiService)
    )
    val movieViewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(apiService)
    )
    val personState by personViewModel.personState
    val persons = personState.persons ?: emptyList()
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
                persons = persons,
                nowPlayings = nowPlayings,
                populars = populars,
                topRateds = topRateds,
                upcomings = upcomings,
                trendingViewModel = trendingViewModel,
                nowPlayingViewModel = nowPlayingViewModel,
                popularViewModel = popularViewModel,
                topRatedViewModel = topRatedViewModel,
                upComingViewModel = upComingViewModel,
                personViewModel = personViewModel,
                apiKey = apiKey,
                onDetailClick = { trendId ->
                    navController.navigate(Screen.DetailTrendScreen.createRoute(trendId.toInt()))
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onCharactorClick = { personId ->
                    navController.navigate(Screen.DetailCharactorScreen.createRoute(personId))
                },
                onClickDetail = {
                    navController.navigate(Screen.DetailCollectionScreen.createRoute(it))
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
        composable(route = Screen.DetailCharactorScreen.route) {
            val personId = it.arguments?.getString("personId")
            if (personId != null) {
                DetailCharactorScreen(
                    personId = personId,
                    apiKey = apiKey,
                    personDetailViewModel = personDetailViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(route = Screen.DetailCollectionScreen.route) {
            val collectionId = it.arguments?.getString("collectionId")
            if (collectionId != null) {
                DetailCollectionScreen(
                    collectionId = collectionId,
                    apiKey = apiKey,
                    collectionViewModel = collectionViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onDetailMovie = { movieId ->
                        navController.navigate(Screen.DetailMovieScreen.createRoute(movieId))
                    }
                )
            }
        }
        composable(route = Screen.DetailMovieScreen.route) {
            val movieId = it.arguments?.getString("movieId")
            if (movieId != null) {
                DetailMovieScreen(
                    movieId = movieId.toInt(),
                    apiKey = apiKey,
                    movieViewModel = movieViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}