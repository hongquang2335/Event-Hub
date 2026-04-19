package com.example.eventticket.ui.screens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventticket.model.CommunityPost
import com.example.eventticket.model.MediaType
import com.example.eventticket.ui.components.Avatar
import com.example.eventticket.ui.components.MediaHero
import com.example.eventticket.ui.components.StateContent
import com.example.eventticket.ui.theme.VibeGreen
import com.example.eventticket.ui.theme.VibeGreenDark
import com.example.eventticket.ui.theme.VibeGreenDeep
import com.example.eventticket.ui.theme.VibeMint
import com.example.eventticket.viewmodel.CommunityViewModel

@Composable
fun CommunityScreen(
    onEventClick: (String) -> Unit,
    onPostClick: (String) -> Unit,
    viewModel: CommunityViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    StateContent(state = state) { data ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 112.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { CommunityHero() }
            item { QuickComposer() }
            items(data.posts) { post ->
                PostCard(
                    post = post,
                    onEventClick = { post.eventId?.let(onEventClick) },
                    onPostClick = { onPostClick(post.id) }
                )
            }
        }
    }
}

@Composable
fun EventCommunityScreen(
    eventId: String,
    onEventClick: (String) -> Unit,
    onPostClick: (String) -> Unit,
    viewModel: CommunityViewModel = viewModel()
) {
    LaunchedEffect(eventId) {
        viewModel.loadPosts(eventId)
    }
    CommunityScreen(onEventClick = onEventClick, onPostClick = onPostClick, viewModel = viewModel)
}

@Composable
fun PostDetailScreen(postId: String, onEventClick: (String) -> Unit, viewModel: CommunityViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    StateContent(state = state) { data ->
        val post = data.posts.firstOrNull { it.id == postId } ?: data.posts.first()
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { PostCard(post = post, onEventClick = { post.eventId?.let(onEventClick) }, onPostClick = {}) }
            item {
                Text("Bình luận", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            items(listOf("Mình cũng đang tìm nhóm đi chung.", "Vé Fanpit còn ít, nên đặt sớm.", "Có ai bán lại vé Standard không?")) {
                Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Row(modifier = Modifier.padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Avatar(it)
                        Column {
                            Text("FanZone member", fontWeight = FontWeight.SemiBold)
                            Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CommunityHero() {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = VibeMint)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(324.dp).padding(22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    "Khám phá\ncộng đồng\nngay!",
                    color = VibeGreenDeep,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Chia sẻ khoảnh khắc, tìm bạn đi cùng và theo dõi những bài viết nóng nhất quanh sự kiện.",
                    color = VibeGreenDark,
                    style = MaterialTheme.typography.bodyMedium
                )
                Surface(shape = RoundedCornerShape(8.dp), color = VibeGreen) {
                    Text("Đăng bài", modifier = Modifier.padding(horizontal = 18.dp, vertical = 9.dp), color = VibeGreenDeep, style = MaterialTheme.typography.labelLarge)
                }
            }
            Box(
                modifier = Modifier.weight(0.82f).height(230.dp).background(
                    Brush.linearGradient(listOf(Color(0xFF006D3D), Color(0xFF2DC275), Color(0xFFFDE68A))),
                    RoundedCornerShape(8.dp)
                )
            )
        }
    }
}

@Composable
private fun QuickComposer() {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Avatar("You")
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Chia sẻ cảm nghĩ về sự kiện...") },
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {}) { Icon(Icons.Default.PhotoCamera, contentDescription = "Ảnh", tint = VibeGreenDark) }
                IconButton(onClick = {}) { Icon(Icons.Default.Videocam, contentDescription = "Video", tint = VibeGreenDark) }
                IconButton(onClick = {}) { Icon(Icons.Default.Mic, contentDescription = "Ghi âm", tint = VibeGreenDark) }
            }
        }
    }
}

@Composable
private fun PostCard(
    post: CommunityPost,
    onEventClick: () -> Unit,
    onPostClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onPostClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Avatar(post.authorName)
                Column(modifier = Modifier.weight(1f)) {
                    Text(post.authorName, fontWeight = FontWeight.Bold)
                    Text(post.postedAt, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Text(post.body, style = MaterialTheme.typography.bodyMedium)
            post.eventTitle?.let {
                AssistChip(onClick = onEventClick, label = { Text(it) })
            }
            when (post.mediaType) {
                MediaType.Image -> MediaHero(title = post.eventTitle ?: "FanZone media", compact = true)
                MediaType.Video -> MediaPlaceholder("Video highlight")
                MediaType.Audio -> MediaPlaceholder("Ghi âm")
                null -> Unit
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconText(Icons.Default.FavoriteBorder, post.likeCount.toString())
                IconText(Icons.Default.ChatBubbleOutline, post.commentCount.toString())
                IconText(Icons.Default.Share, post.shareCount.toString())
                IconText(Icons.Default.BookmarkBorder, if (post.isSaved) "Đã lưu" else "Lưu")
            }
        }
    }
}

@Composable
private fun MediaPlaceholder(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun IconText(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
