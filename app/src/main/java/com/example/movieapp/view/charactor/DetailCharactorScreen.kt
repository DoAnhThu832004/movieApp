package com.example.movieapp.view.charactor

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.model.Constants
import com.example.movieapp.viewmodel.PersonDetailViewModel
import com.example.movieapp.viewmodel.PersonViewModel

@Composable
fun DetailCharactorScreen(
    personId: String,
    apiKey: String,
    personDetailViewModel: PersonDetailViewModel,
    onBackClick: () -> Unit
) {
    val personDetailState by personDetailViewModel.personDetailState
    val personDetail = personDetailState.personsDetail
    LaunchedEffect(personId) {
        personDetailViewModel.getPersonDetail(
            apiKey = apiKey,
            personId = personId.toInt()
        )
    }
//    Box(modifier = Modifier.fillMaxSize()) {
//        when {
//            personDetailState.isLoading -> {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//            personDetailState.error != null -> {
//                Text(text = personDetailState.error!!, modifier = Modifier.align(Alignment.Center))
//            }
//            personDetailState.personsDetail != null -> {
//                val data = personDetailState.personsDetail!!
//                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                    // Hiển thị tên
//                    Text(text = data.name, style = MaterialTheme.typography.h4)
//
//                    // Hiển thị tiểu sử (biography)
//                    Text(text = data.biography)
//
//                    // Hiển thị ngày sinh (birthday)
//                    data.birthday?.let {
//                        Text(text = "Ngày sinh: $it")
//                    }
//                }
//            }
//        }
//    }
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
                    onClick = {
                        onBackClick()
                    }
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
                Text(text = personDetail.name)
                // Thêm các dòng dưới đây để kiểm tra thông tin khác
                Text(text = "Birthday: ${personDetail.birthday ?: "No data"}")
                Text(text = "Biography: ${personDetail.biography ?: "No data"}")
            } else {
                // Hiển thị thông báo nếu đang tải hoặc không có dữ liệu
                Text(text = "Đang tải dữ liệu...")
            }
        }
    }
}