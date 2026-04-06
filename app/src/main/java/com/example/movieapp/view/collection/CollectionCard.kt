package com.example.movieapp.view.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.model.Constants

@Composable
fun CollectionCard(
    collectionName: String,
    image: String,
    onClickDetail: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(220.dp)
            .padding(8.dp)
            .clickable { onClickDetail()},
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.Gray.copy(alpha = 0.3f)
        ) {}
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp, end = 12.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp
        ) {
            Box {
                AsyncImage(
                    model = "${Constants.IMAGE_BASE_URL}$image",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )
                Text(
                    text = collectionName.uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}