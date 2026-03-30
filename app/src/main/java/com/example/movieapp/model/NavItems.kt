package com.example.movieapp.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val label: String,
    val icon: ImageVector
)
data class TabItem(
    val label: String,
    val color: Color
)