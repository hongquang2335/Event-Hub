package com.example.myapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.model.CommunityPost
import com.example.myapplication.model.Event
import com.example.myapplication.model.EventMoment
import com.example.myapplication.model.TicketStatus
import com.example.myapplication.model.TicketTier
import com.example.myapplication.model.TicketWalletItem
import com.example.myapplication.model.TierStatus
import com.example.myapplication.ui.theme.Danger
import com.example.myapplication.ui.theme.Evergreen
import com.example.myapplication.ui.theme.EvergreenDark
import com.example.myapplication.ui.theme.Ink
import com.example.myapplication.ui.theme.LavenderWash
import com.example.myapplication.ui.theme.MintWash
import com.example.myapplication.ui.theme.PeachWash
import com.example.myapplication.ui.theme.SoftLine
import com.example.myapplication.ui.theme.SoftText
import com.example.myapplication.ui.theme.SurfaceCard
import com.example.myapplication.ui.theme.Warning

@Composable
fun SectionHeader(title: String, subtitle: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            subtitle?.let {
                Text(it, color = SoftText, style = MaterialTheme.typography.bodyMedium)
            }
        }
        if (subtitle != null && subtitle.startsWith("Xem")) {
            Text(subtitle, color = Evergreen, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun CircleAvatar(size: Dp = 44.dp) {
    Image(
        painter = painterResource(R.drawable.fan_zone_avatar),
        contentDescription = "Avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}

@Composable
fun HomeSwitchChip(
    label: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (active) Evergreen else Color.Transparent)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (active) Color.White else Ink, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun CategoryPill(emoji: String, label: String) {
    Column(
        modifier = Modifier
            .width(74.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .padding(vertical = 14.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(emoji, fontSize = 20.sp)
        Text(label, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun FlowCategoryRow(categories: List<Pair<String, String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        categories.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowItems.forEach { (emoji, label) ->
                    CategoryPill(emoji, label)
                }
            }
        }
    }
}

@Composable
fun HeroBanner(event: Event, onOpenEvent: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF24B66B), EvergreenDark, Color(0xFF053B27))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.widthIn(max = 210.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AssistChip(onClick = {}, label = { Text("Giam chop nhoang") })
            Text("Flash Sale\nCuoi Tuan!", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text("Giam gia len den 50% cho cac su kien hot nhat tuan nay.", color = Color.White.copy(alpha = 0.88f))
            OutlinedButton(
                onClick = onOpenEvent,
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.36f)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Xem ngay", color = Color.White)
            }
        }
        Image(
            painter = painterResource(event.imageRes),
            contentDescription = event.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(140.dp)
                .clip(RoundedCornerShape(24.dp))
        )
    }
}

@Composable
fun EventCard(event: Event, modifier: Modifier = Modifier, onOpen: () -> Unit) {
    Card(
        modifier = modifier.clickable(onClick = onOpen),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(26.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(event.imageRes),
                    contentDescription = event.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                )
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.92f)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("THANG 10", style = MaterialTheme.typography.labelLarge, color = Danger)
                        Text("28", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(event.title, style = MaterialTheme.typography.titleLarge)
                MetaRow(Icons.Default.Schedule, event.schedule)
                MetaRow(Icons.Default.LocationOn, "${event.venue}, ${event.city}")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("+99 quan tam", color = SoftText, style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = onOpen, shape = RoundedCornerShape(20.dp)) {
                        Text("Quan tam")
                    }
                }
            }
        }
    }
}

@Composable
fun GradientPanel(title: String, body: String, action: String, onAction: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF052C1D), EvergreenDark, Color(0xFF4AD38E))))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.widthIn(max = 220.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(title, style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(body, color = Color.White.copy(alpha = 0.88f))
            Button(onClick = onAction, shape = RoundedCornerShape(20.dp)) {
                Text(action)
            }
        }
    }
}

@Composable
fun CommunityCard(post: CommunityPost) {
    Card(shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            AssistChip(
                onClick = {},
                label = { Text(post.topic) },
                leadingIcon = { Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(16.dp)) }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                CircleAvatar()
                Column {
                    Text(post.author, fontWeight = FontWeight.Bold)
                    Text(post.role, color = SoftText, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Text(post.content, style = MaterialTheme.typography.bodyLarge)
            if (post.imageRes != null) {
                Image(
                    painter = painterResource(post.imageRes),
                    contentDescription = post.topic,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                IconText(Icons.Default.FavoriteBorder, "${post.likes}")
                IconText(Icons.AutoMirrored.Outlined.Chat, "${post.comments}")
                IconText(Icons.AutoMirrored.Filled.ArrowForward, "Chia se")
            }
        }
    }
}

@Composable
fun TicketCard(item: TicketWalletItem, onOpenEvent: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.clickable { onOpenEvent(item.eventId) }
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(item.eventTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(item.schedule, color = SoftText)
                    Text(item.venue, color = SoftText)
                }
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MintWash),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.QrCode2, contentDescription = null, tint = Evergreen, modifier = Modifier.size(34.dp))
                }
            }
            Surface(shape = RoundedCornerShape(20.dp), color = LavenderWash, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(item.seatLabel, fontWeight = FontWeight.SemiBold)
                    Text("Ma QR: ${item.qrCode}", style = MaterialTheme.typography.bodyMedium, color = SoftText)
                }
            }
            Button(onClick = { onOpenEvent(item.eventId) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(22.dp)) {
                Text("Xem chi tiet su kien")
            }
        }
    }
}

@Composable
fun ProfileActionRow(title: String, subtitle: String, clickable: Boolean, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.clickable(enabled = clickable, onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = SoftText)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = SoftText)
        }
    }
}

@Composable
fun MessageBubble(author: String, body: String, isMine: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = if (isMine) MintWash else Color.White,
            tonalElevation = 2.dp,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(author, fontWeight = FontWeight.SemiBold, color = if (isMine) Evergreen else Ink)
                Text(body)
            }
        }
    }
}

@Composable
fun InfoPanel(rows: List<Pair<ImageVector, String>>) {
    Card(shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            rows.forEachIndexed { index, row ->
                MetaRow(row.first, row.second)
                if (index != rows.lastIndex) HorizontalDivider()
            }
        }
    }
}

@Composable
fun ArtistRow(artists: List<String>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(artists) { artist ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CircleAvatar(size = 56.dp)
                Text(artist, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun TimelineColumn(timeline: List<EventMoment>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        timeline.forEach { moment ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Evergreen))
                    Box(modifier = Modifier.width(2.dp).height(34.dp).background(SoftLine))
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(moment.time, color = Evergreen, fontWeight = FontWeight.Bold)
                    Text(moment.title)
                }
            }
        }
    }
}

@Composable
fun VenueMapCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(Color(0xFFF5F0EC))
                    .border(1.dp, SoftLine, RoundedCornerShape(26.dp))
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                        .width(220.dp)
                        .height(58.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Evergreen),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Zone A (VIP)", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MapZone("Zone B", Modifier.weight(1f))
                    MapZone("Zone B", Modifier.weight(1f))
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.82f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFFE8E0DA)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Zone C (Early Bird)", color = SoftText)
                }
            }
            Text("Venue map responsive: tren tablet co the doi sang layout 2 cot voi panel chon ve ben canh.", color = SoftText, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun MapZone(label: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .height(72.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .border(1.dp, SoftLine, RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun NoticeCard(note: String) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(1.dp, SoftLine)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Star, contentDescription = null, tint = Evergreen)
            Text(note, color = SoftText)
        }
    }
}

@Composable
fun TierCard(
    tier: TicketTier,
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Card(shape = RoundedCornerShape(26.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(
                                    when (tier.status) {
                                        TierStatus.AVAILABLE -> Evergreen
                                        TierStatus.LIMITED -> Warning
                                        TierStatus.SOLD_OUT -> SoftLine
                                    }
                                )
                        )
                        Text(tier.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Text(tier.benefits, color = SoftText)
                    if (tier.status == TierStatus.SOLD_OUT) {
                        Text(
                            formatPrice(tier.price),
                            color = SoftText,
                            textDecoration = TextDecoration.LineThrough,
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else {
                        Text(formatPrice(tier.price), color = Evergreen, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
                    }
                }
                when (tier.status) {
                    TierStatus.LIMITED -> StateTag("Sap het", PeachWash, Warning)
                    TierStatus.SOLD_OUT -> StateTag("Het ve", Color(0xFFF3F0EF), SoftText)
                    TierStatus.AVAILABLE -> StateTag("Mo ban", MintWash, Evergreen)
                }
            }
            QuantityStepper(
                quantity = quantity,
                enabled = tier.status != TierStatus.SOLD_OUT,
                onDecrease = onDecrease,
                onIncrease = onIncrease
            )
        }
    }
}

@Composable
fun QuantityStepper(quantity: Int, enabled: Boolean, onDecrease: () -> Unit, onIncrease: () -> Unit) {
    Surface(shape = RoundedCornerShape(22.dp), color = SurfaceCard, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDecrease, enabled = enabled && quantity > 0) {
                Icon(Icons.Outlined.Remove, contentDescription = "Giam")
            }
            Text("$quantity", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            IconButton(onClick = onIncrease, enabled = enabled) {
                Icon(Icons.Outlined.Add, contentDescription = "Tang")
            }
        }
    }
}

@Composable
fun CheckoutSummaryCard(event: Event, activeLines: List<TicketTier>, quantities: Map<String, Int>) {
    Card(shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text("Tom tat don hang", style = MaterialTheme.typography.titleLarge)
            Text(event.title, fontWeight = FontWeight.Bold)
            activeLines.forEach { tier ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${tier.name} x${quantities[tier.id] ?: 0}")
                    Text(formatPrice(tier.price * (quantities[tier.id] ?: 0)), fontWeight = FontWeight.SemiBold)
                }
            }
            HorizontalDivider()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tong thanh toan", style = MaterialTheme.typography.titleMedium)
                Text(
                    formatPrice(activeLines.sumOf { it.price * (quantities[it.id] ?: 0) }),
                    style = MaterialTheme.typography.titleLarge,
                    color = Evergreen,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun PaymentMethodCard(
    label: String,
    subtitle: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(22.dp),
        color = if (selected) MintWash else SurfaceCard,
        border = BorderStroke(1.dp, if (selected) Evergreen else SoftLine)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(label, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = SoftText, style = MaterialTheme.typography.bodyMedium)
            }
            Icon(
                imageVector = if (selected) Icons.Default.CheckCircle else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = if (selected) Evergreen else SoftText
            )
        }
    }
}

@Composable
fun EmptyStateCard(title: String, body: String) {
    Card(shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(Icons.Default.RemoveRedEye, contentDescription = null, tint = SoftText)
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(body, color = SoftText, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun TicketStatusFilter(selected: TicketStatus, onSelect: (TicketStatus) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        TicketStatus.values().forEach { status ->
            FilterChip(
                selected = selected == status,
                onClick = { onSelect(status) },
                label = {
                    Text(
                        when (status) {
                            TicketStatus.UPCOMING -> "Sap dien ra"
                            TicketStatus.COMPLETED -> "Da dung"
                            TicketStatus.CANCELLED -> "Da huy"
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun StateTag(label: String, background: Color, foreground: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(label, color = foreground, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun HorizontalDivider() {
    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(SoftLine))
}

@Composable
fun MetaRow(icon: ImageVector, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Evergreen, modifier = Modifier.size(18.dp))
        Text(text, color = SoftText)
    }
}

@Composable
fun IconText(icon: ImageVector, label: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = SoftText, modifier = Modifier.size(18.dp))
        Text(label, color = SoftText)
    }
}

fun formatPrice(price: Int): String = "%,dđ".format(price).replace(',', '.')
