package com.example.movieapp.view.general

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.Response.NowPlaying
import com.example.movieapp.model.Response.Popular
import com.example.movieapp.model.Response.TopRated
import com.example.movieapp.model.Response.UpComing
import com.example.movieapp.model.TabItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlayPage(
    nowPlayings: List<NowPlaying>,
    populars: List<Popular>,
    topRateds: List<TopRated>,
    upcomings: List<UpComing>,
    onBackClick: () -> Unit
) {
    val tabs = listOf(
        TabItem("Now Playing", Color(0xFF6C63FF)),
        TabItem("Popular", Color(0xFFFF4D86)),
        TabItem("Top Rated", Color(0xFF2ED3B7)),
        TabItem("Upcoming", Color(0xFFFFC107))
    )
    var selectedIndex by remember { mutableStateOf(0) }
    val animatedHeaderColor by animateColorAsState(
        targetValue = tabs[selectedIndex].color,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 150f),
        label = "HeaderColorAnimation"
    )
    val density = LocalDensity.current
    var tabBounds by remember { mutableStateOf(List(tabs.size) { Rect.Zero }) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(animatedHeaderColor)
                    .statusBarsPadding()
                    .padding(bottom = 40.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    androidx.compose.material.Text(
                        text = "",
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
        val targetRect = tabBounds.getOrNull(selectedIndex) ?: Rect.Zero
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            if(targetRect.width > 0f) {
                Box(
                    modifier = Modifier
                        .offset{ IntOffset(targetRect.left.toInt(),0) }
                        .width(with(density) {targetRect.width.toDp()})
                        .fillMaxHeight()
                        .background(
                            color = animatedHeaderColor,
                            shape = getHeaderTabShape(
                                flareWidth = with(density) { 16.dp.toPx() }, //flare kéo dài đc bang nhieu
                                flareHeight = with(density) { 32.dp.toPx() },
                                cornerSize = with(density) { 24.dp.toPx() },
                                hasStartFlare = selectedIndex > 0,
                                hasEndFlare = selectedIndex < tabs.size - 1
                            )
                        )
                )
            }
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                tabs.forEachIndexed { index, tab ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .onGloballyPositioned { coords ->
                                val pos = coords.positionInParent() // lay toa do
                                tabBounds = tabBounds.toMutableList().apply {
                                    this[index] = Rect(
                                        pos.x, pos.y,
                                        pos.x + coords.size.width,
                                        pos.y + coords.size.height
                                    )
                                }
                            }
                            .clickable { selectedIndex = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab.label,
                            color = if (selectedIndex == index) Color.White else MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }
        // Bổ sung Modifier.weight(1f) là điều kiện bắt buộc để khối này chiếm toàn bộ phần màn hình còn lại
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            val startOffset = -maxWidth.value // Sử dụng .value để lấy giá trị số thực của Float thay vì Dp tĩnh

            val filteredReports = remember(selectedIndex, nowPlayings, populars, topRateds, upcomings) {
                when(selectedIndex) {
                    0 -> nowPlayings
                    1 -> populars
                    2 -> topRateds
                    else -> upcomings
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 16.dp)
            ) {
                itemsIndexed(
                    items = filteredReports,
                    key = { index, _ -> "tab_$selectedIndex-item_$index" }
                ) { index, item ->
                    val alphaAnim = remember { androidx.compose.animation.core.Animatable(0f) }
                    val slideAnim = remember { androidx.compose.animation.core.Animatable(startOffset) }

                    LaunchedEffect(key1 = selectedIndex, key2 = index) {
                        alphaAnim.snapTo(0f)
                        slideAnim.snapTo(startOffset)

                        delay(index.coerceAtMost(12) * 50L)

                        launch {
                            alphaAnim.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(durationMillis = 400)
                            )
                        }
                        launch {
                            slideAnim.animateTo(
                                targetValue = 0f,
                                animationSpec = spring(
                                    dampingRatio = 0.75f,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(slideAnim.value.toInt(), 0) }
                            .alpha(alphaAnim.value)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        val movieTitle = when (item) {
                            is NowPlaying -> item.title ?: "Đang cập nhật"
                            is Popular -> item.title ?: "Đang cập nhật"
                            is TopRated -> item.title ?: "Đang cập nhật"
                            is UpComing -> item.title ?: "Đang cập nhật"
                            else -> "Dữ liệu không xác định"
                        }

                        Text(
                            text = movieTitle,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}
fun getHeaderTabShape(
    flareWidth: Float,
    flareHeight: Float,
    cornerSize: Float,
    hasStartFlare: Boolean,
    hasEndFlare: Boolean
) = GenericShape { size, _ ->
    val fw = flareWidth
    val fh = flareHeight
    val cs = cornerSize
    val w = size.width
    val h = size.height

    // Xử lý điểm bắt đầu và đường cong bên trái
    if (hasStartFlare) {
        moveTo(0f, 0f)
        // Vẽ đường cong S-curve từ Header xuống cạnh Tab
        cubicTo(fw * 0.8f, 0f, fw, fh * 0.4f, fw, fh)
        lineTo(fw, h - cs)
    } else {
        moveTo(0f, 0f)
        lineTo(0f, h - cs)
    }

    // Bo tròn góc dưới bên trái của Tab
    val lx = if (hasStartFlare) fw else 0f
    cubicTo(lx, h - (cs * 0.4f), lx + (cs * 0.4f), h, lx + cs, h)

    // Bo tròn góc dưới bên phải của Tab
    val rx = w - (if (hasEndFlare) fw else 0f)
    lineTo(rx - cs, h)
    cubicTo(rx - (cs * 0.4f), h, rx, h - (cs * 0.4f), rx, h - cs)

    // Xử lý đường cong bên phải và kết thúc tại góc trên bên phải
    if (hasEndFlare) {
        lineTo(rx, fh)
        cubicTo(rx, fh * 0.4f, rx + (fw * 0.2f), 0f, w, 0f)
    } else {
        lineTo(w, 0f)
    }
    close()
}