package com.example.movieapp.view.general

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.NavItems
import com.example.movieapp.model.Response.NowPlaying
import com.example.movieapp.model.Response.Popular
import com.example.movieapp.model.Response.TopRated
import com.example.movieapp.model.Response.Trending
import com.example.movieapp.model.Response.UpComing
import com.example.movieapp.viewmodel.NowPlayingViewModel
import com.example.movieapp.viewmodel.PopularViewModel
import com.example.movieapp.viewmodel.TopRatedViewModel
import com.example.movieapp.viewmodel.TrendingViewModel
import com.example.movieapp.viewmodel.UpcomingViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    trends: List<Trending>,
    nowPlayings: List<NowPlaying>,
    populars: List<Popular>,
    topRateds: List<TopRated>,
    upcomings: List<UpComing>,
    trendingViewModel: TrendingViewModel,
    nowPlayingViewModel: NowPlayingViewModel,
    popularViewModel: PopularViewModel,
    topRatedViewModel: TopRatedViewModel,
    upComingViewModel: UpcomingViewModel,
    apiKey: String,
    onDetailClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val navItemsList = listOf(
        NavItems("Trang chu",Icons.Default.Home),
        NavItems("Play",Icons.Default.PlayArrow),
        NavItems("Ho so",Icons.Default.Person),
    )
    var selectIndex by rememberSaveable { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        nowPlayingViewModel.getNowPlaying(apiKey)
        popularViewModel.getPopular(apiKey)
        topRatedViewModel.getTopRated(apiKey)
        upComingViewModel.getUpcoming(apiKey)
    }
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
            nowPlayings = nowPlayings,
            populars = populars,
            topRateds = topRateds,
            upcomings = upcomings,
            trendingViewModel = trendingViewModel,
            apiKey = apiKey,
            onDetailClick = onDetailClick,
            onBackClick = onBackClick
        )
    }
}
@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    trends: List<Trending>,
    nowPlayings: List<NowPlaying>,
    populars: List<Popular>,
    topRateds: List<TopRated>,
    upcomings: List<UpComing>,
    trendingViewModel: TrendingViewModel,
    apiKey: String,
    onDetailClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    when(selectedIndex) {
        0 -> HomePage(trends = trends,trendingViewModel,apiKey, onDetailClick)
        1 -> PlayPage(
            nowPlayings = nowPlayings,
            populars = populars,
            topRateds = topRateds,
            upcomings = upcomings,
            onBackClick = onBackClick
        )
        2 -> ProfilePage()
    }
}