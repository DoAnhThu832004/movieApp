package com.example.movieapp.view.charactor

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.model.Constants
import com.example.movieapp.viewmodel.PersonViewModel

@SuppressLint("RememberReturnType")
@Composable
fun DetailCharactorScreen(
    personId: String,
    apiKey: String,
    personViewModel: PersonViewModel
) {
    val personDetailState = personViewModel.personDetailState.value
    val personDetail = personDetailState.personsDetail
    LaunchedEffect(personId) {
        personViewModel.getPersonDetail(
            apiKey = apiKey,
            personId = personId.toInt()
        )
    }
    val imageUrl = "${Constants.IMAGE_BASE_URL}${personDetail?.profile_path}"
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back"
                    )
                }
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = personDetail?.name,
                    modifier = Modifier
                        .size(200.dp)
                )
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Back"
                    )
                }
            }
        }
        item {
            if (personDetail != null) {
                Text(
                    text = personDetail.name
                )
            }
        }
    }
}