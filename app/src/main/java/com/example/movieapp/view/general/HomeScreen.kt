package com.example.movieapp.view.general

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.NavItems
import com.example.movieapp.model.Response.Trending
import com.example.movieapp.viewmodel.TrendingViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    trends: List<Trending>,
    trendingViewModel: TrendingViewModel,
    apiKey: String
) {
    val navItemsList = listOf(
        NavItems("Trang chu",Icons.Default.Home),
        NavItems("Play",Icons.Default.PlayArrow),
        NavItems("Ho so",Icons.Default.Person),
    )
    var selectIndex by rememberSaveable { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            CustomFloatingBottomBar(
                items = navItemsList,
                selectedIndex = selectIndex,
                onItemClick = { index -> selectIndex = index}
            )
        },
        modifier = modifier.padding(bottom = 24.dp)
    ) {
        ContentScreen(
            modifier = Modifier.padding(it),
            selectedIndex = selectIndex,
            trends = trends,
            trendingViewModel = trendingViewModel,
            apiKey = apiKey
        )
    }
}
@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    trends: List<Trending>,
    trendingViewModel: TrendingViewModel,
    apiKey: String
) {
    when(selectedIndex) {
        0 -> HomePage(trends = trends,trendingViewModel,apiKey)
        1 -> PlayPage()
        2 -> ProfilePage()
    }
}