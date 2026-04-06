package com.example.movieapp.view

import okhttp3.Route

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object TrendScreen : Screen("trend_screen")
    object DetailTrendScreen : Screen("detail_trend_screen?trendId={trendId}") {
        fun createRoute(trendId: Int) = "detail_trend_screen?trendId=$trendId"
    }
    object DetailCharactorScreen: Screen("detail_charactor_screen?personId={personId}") {
        fun createRoute(personId: Int) = "detail_charactor_screen?personId=$personId"
    }
    object DetailCollectionScreen: Screen("detail_collection_screen?collectionId={collectionId}") {
        fun createRoute(collectionId: Int) = "detail_collection_screen?collectionId=$collectionId"
    }
    object DetailMovieScreen: Screen("detail_movie_screen?movieId={movieId}") {
        fun createRoute(movieId: Int) = "detail_movie_screen?movieId=$movieId"
    }
}
