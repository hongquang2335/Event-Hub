package com.example.eventticket.ui.screens.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventticket.ui.components.Avatar
import com.example.eventticket.ui.components.MediaHero
import com.example.eventticket.ui.components.Metric
import com.example.eventticket.ui.components.StateContent
import com.example.eventticket.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    StateContent(state = state) { profile ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 112.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(modifier = Modifier.padding(16.dp)) {
                    MediaHero(title = "FanZone profile", modifier = Modifier.height(190.dp), compact = true)
                    Avatar(
                        name = profile.name,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp)
                            .clip(CircleShape)
                    )
                }
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(profile.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(profile.bio, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {}) { Text("Chỉnh sửa") }
                        OutlinedButton(onClick = {}) { Text("Kết bạn") }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Metric(profile.joinedEvents.toString(), "Sự kiện")
                        Metric(profile.followers.toString(), "Follower")
                        Metric(profile.postCount.toString(), "Bài đăng")
                    }
                }
            }
            item { ProfileSection("Sự kiện yêu thích", "Aurora Music Nights, Esports Grand Final") }
            item { ProfileSection("Followers / Following", "${profile.followers} followers - ${profile.following} following") }
            item { ProfileSection("Bạn bè / kết bạn", "${profile.friends} bạn bè, gợi ý kết bạn theo sự kiện đã tham gia") }
            item { ProfileSection("Bài đăng cá nhân", "Hiển thị feed cá nhân, media và sự kiện liên quan.") }
            item { ProfileSection("Vé của tôi", "Lối tắt đến vé sắp diễn ra, resale và lịch sử check-in.") }
            item {
                OutlinedButton(onClick = onSignOut, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text("Đăng xuất")
                }
            }
        }
    }
}

@Composable
private fun ProfileSection(title: String, body: String) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(body, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
