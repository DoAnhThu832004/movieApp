package com.example.movieapp.view.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.Response.Trending
import com.example.movieapp.view.trending.TrendScreen
import com.example.movieapp.viewmodel.TrendingViewModel

@Composable
fun HomePage(
    trends: List<Trending>,
    trendingViewModel: TrendingViewModel,
    apiKey: String
) {
    LaunchedEffect(Unit) {
        trendingViewModel.getTrending(apiKey)
    }
    val previewSongs = trends.take(8)
    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 8.dp)
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Trending"
                )
            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(previewSongs, key = {it.id}) { trends ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        TrendScreen(trend = trends)
                    }
                }
            }
        }
    }
}