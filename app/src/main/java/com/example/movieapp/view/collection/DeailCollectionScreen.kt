package com.example.movieapp.view.collection

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.model.Constants
import com.example.movieapp.viewmodel.CollectionViewModel

@Composable
fun DetailCollectionScreen(
    collectionId: String,
    apiKey: String,
    collectionViewModel: CollectionViewModel,
    onBackClick: () -> Unit,
    onDetailMovie: (Int) -> Unit
) {
    val collectionState by collectionViewModel.collectionState
    val collections = collectionState.collection
    val imageUrl = "${Constants.IMAGE_BASE_URL}${collections?.backdrop_path}"
    LaunchedEffect(collectionId) {
        collectionViewModel.getCollection(
            collectionId = collectionId.toInt(),
            apiKey = apiKey
        )
    }
//    Box(modifier = Modifier.fillMaxSize()) {
//        when {
//            collectionState.isLoading -> {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//            collectionState.error != null -> {
//                Text(text = collectionState.error!!, modifier = Modifier.align(Alignment.Center))
//            }
//            collectionState.collection != null -> {
//                val data = collectionState.collection!!
//                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                    // Hiển thị tên
//                    Text(text = data.name, style = MaterialTheme.typography.h4)
//                }
//            }
//        }
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            ) {
                IconButton(
                    onClick = {onBackClick()}
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = collections?.name ?: "No data",
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = collections?.overview ?: "No data",
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (collections != null) {
                items(collections.parts) {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(300.dp)
                            .padding(start = 16.dp)
                            .clickable { onDetailMovie(it.id) }
                    ) {
                        val imageUrl = "${Constants.IMAGE_BASE_URL}${it.poster_path}"
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(25.dp)),
                        )
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 16.dp, end = 8.dp),
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                                Text(
                                    text = String.format("%.1f", it.vote_average),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}