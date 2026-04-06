package com.example.movieapp.view.movie

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.model.Constants
import com.example.movieapp.view.Screen
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun DetailMovieScreen(
    movieId: Int,
    apiKey: String,
    movieViewModel: MovieViewModel,
    onBackClick: () -> Unit
) {
    val movieState by movieViewModel.movieState
    val movie = movieState.movie
    val imageUrl = "${Constants.IMAGE_BASE_URL}${movie?.backdrop_path}"
    LaunchedEffect(movieId) {
        movieViewModel.getMovieDetail(
            movieId = movieId,
            apiKey = apiKey
        )
    }
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
                    .align(Alignment.Center)
                    .padding(8.dp),
                color = Color.Blue,
                shape = RoundedCornerShape(8.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
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
                Row(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    Text(
                        text = String.format("%.1f", movie?.vote_average),
                        color = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie?.title ?: "thu",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie?.overview ?: "thu"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Genres",
            fontSize = 20.sp
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (movie != null) {
                items(movie.genres) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .width(100.dp)
                            .height(40.dp)
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}