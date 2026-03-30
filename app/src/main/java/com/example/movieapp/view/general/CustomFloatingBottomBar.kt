package com.example.movieapp.view.general

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.NavItems

@Composable
fun CustomFloatingBottomBar(
    modifier: Modifier = Modifier,
    items: List<NavItems>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(36.dp),
                    spotColor = Color.Black.copy(0.6f)
                )
                .clip(RoundedCornerShape(36.dp))
                .background(Color(0xFF2B2939).copy(alpha = 0.95f))
                .border(1.dp, Color(0xFF2B2939).copy(alpha = 0.95f), RoundedCornerShape(36.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = selectedIndex == index
                    val iconColor by animateColorAsState(
                        targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.4f),
                        label = "iconColor"
                    )
                    CustomNavItem(
                        icon = item.icon,
                        description = item.label,
                        isSelected = isSelected,
                        iconTintColor = iconColor,
                        onClick = { onItemClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomNavItem(
    icon: ImageVector,
    description: String,
    isSelected: Boolean,
    iconTintColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color(0xFF6C63FF) else Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = iconTintColor,
            modifier = Modifier.size(26.dp)
        )
    }
}