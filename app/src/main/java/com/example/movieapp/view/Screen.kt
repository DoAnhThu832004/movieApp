package com.example.movieapp.view

import okhttp3.Route

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
}