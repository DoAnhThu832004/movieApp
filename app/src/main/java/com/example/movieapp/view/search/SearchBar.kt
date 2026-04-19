package com.example.movieapp.view.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.model.Constants
import com.example.movieapp.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    api_key: String,
    //onArtistClick: (Artist) -> Unit = {},
    //onSongClick: (Song) -> Unit = {}
) {
    //val suggestion by searchViewModel.suggestions.collectAsState()
    //val sSong by searchViewModel.sSong.collectAsState()
    //val sArtist by searchViewModel.sArtist.collectAsState()
    val collection by searchViewModel.collection.collectAsState()
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(collection) {
        if(collection.isNotEmpty()) {
            expanded = true
        }
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded =! expanded},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {new ->
                query = new
                searchViewModel.onQueryChanged(new,api_key)
                if(new.isEmpty()) expanded = false
            },
            label = {
                Text(
                    text = "Tim kiem",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
        )
        if (collection.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}
            ) {
                collection.forEach { artist ->
                    val imageUrl = "${Constants.IMAGE_BASE_URL}${artist.poster_path}"
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if(artist.poster_path.isNullOrEmpty()) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(shape = RoundedCornerShape(50.dp)),
                                    )
                                } else {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(shape = RoundedCornerShape(50.dp)),
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = artist.name
                                )
                            }
                        },
                        onClick = {
                            query = artist.name
                            expanded = false
                            searchViewModel.clearSuggestions()
                            //onArtistClick(artist)
                        },
                        contentPadding = PaddingValues(8.dp)
                    )
                }
            }
        }
    }
}