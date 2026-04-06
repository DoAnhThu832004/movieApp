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
import com.example.movieapp.model.CollectItem
import com.example.movieapp.model.Response.Person
import com.example.movieapp.model.Response.Trending
import com.example.movieapp.view.charactor.CharactorScreen
import com.example.movieapp.view.collection.CollectionCard
import com.example.movieapp.view.trending.TrendScreen
import com.example.movieapp.viewmodel.TrendingViewModel

@Composable
fun HomePage(
    trends: List<Trending>,
    persons: List<Person>,
    trendingViewModel: TrendingViewModel,
    apiKey: String,
    onDetailClick: (String) -> Unit,
    onCharactorClick: (Int) -> Unit,
    onClickDetail: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        trendingViewModel.getTrending(apiKey)
    }
    val featuredCollections = listOf(
        CollectItem("John Wick", 403374,"/vViRXFnSyGJ2fzMbcc5sqTKswcd.jpg"),
        CollectItem("Avengers", 86311,"/2UNUv4NJdC36E5myDHACBJ99EwL.jpg"),
        CollectItem("Harry Potter", 1241,"/kmEsQL2vOTA0jnM28fXS45Ky8kX.jpg"),
        CollectItem("Star Wars", 10,"/iY2ujEY2m68OTTlPFTiHub9joHS.jpg"),
        CollectItem("Spider-Man", 556,"/waZqriYTuBE3WqXI3SDGi3kfDQE.jpg"),
        CollectItem("Fast & Furious", 9485,"/z5A5W3WYJc3UVEWljSGwdjDgQ0j.jpg"),
        CollectItem("The Dark Knight", 263,"/xyhrCEdB4XRkelfVsqXeUZ6rLHi.jpg"),
    )
    val previewSongs = trends.take(8)
    val previewChar = persons.take(8)
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
                        TrendScreen(
                            trend = trends,
                            onDetailClick = {
                                onDetailClick(trends.id.toString())
                            }
                        )
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Collection"
                )
            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(featuredCollections) { collection ->
                    CollectionCard(
                        collectionName = collection.name,
                        image = collection.image,
                        onClickDetail = {
                            onClickDetail(collection.id)
                        }
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Charactors"
                )
            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(previewChar) {
                    CharactorScreen(
                        person = it,
                        onDetailClick = {
                            onCharactorClick(it.id)
                        }
                    )
                }
            }
        }
    }
}