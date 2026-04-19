package com.example.eventticket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.eventticket.model.EventStatus
import com.example.eventticket.model.FanEvent
import com.example.eventticket.model.toVnd
import com.example.eventticket.ui.theme.VibeAmber
import com.example.eventticket.ui.theme.VibeBlue
import com.example.eventticket.ui.theme.VibeBlueSoft
import com.example.eventticket.ui.theme.VibeGreen
import com.example.eventticket.ui.theme.VibeGreenDark
import com.example.eventticket.ui.theme.VibeGreenDeep
import com.example.eventticket.ui.theme.VibeMint
import com.example.eventticket.ui.theme.VibeMintSoft
import com.example.eventticket.ui.theme.VibeStroke
import com.example.eventticket.ui.theme.VibeSurfaceMuted
import com.example.eventticket.viewmodel.ScreenState

@Composable
fun <T> StateContent(
    state: ScreenState<T>,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    when (state) {
        ScreenState.Loading -> LoadingState(modifier)
        is ScreenState.Empty -> MessageState(message = state.message, modifier = modifier)
        is ScreenState.Error -> MessageState(message = state.message, modifier = modifier, isError = true)
        is ScreenState.Ready -> content(state.data)
    }
}

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun MessageState(message: String, modifier: Modifier = Modifier, isError: Boolean = false) {
    Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = if (isError) Icons.Default.ErrorOutline else Icons.Default.Event,
            contentDescription = null,
            tint = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        )
        Text(text = message, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun MediaHero(
    title: String,
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(if (compact) 1.86f else 1.41f)
            .clip(RoundedCornerShape(8.dp))
            .background(eventBrush(title))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Image,
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd),
            tint = Color.White.copy(alpha = 0.82f)
        )
        Text(
            text = title,
            modifier = Modifier.align(Alignment.BottomStart),
            color = Color.White,
            style = if (compact) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun FlashSaleBanner(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = VibeGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(254.dp).padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(126.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(eventBrush("flash sale")),
                contentAlignment = Alignment.Center
            ) {
                Text("50%", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(shape = RoundedCornerShape(4.dp), color = VibeGreenDeep.copy(alpha = 0.16f)) {
                    Text(
                        "GIẢM CHỚP NHOÁNG",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = VibeGreenDeep,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Text(
                    "Flash Sale\nCuối Tuần!",
                    color = VibeGreenDeep,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Giảm giá lên đến 50% cho các sự kiện hot nhất tuần này.",
                    color = VibeGreenDeep.copy(alpha = 0.78f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Surface(shape = RoundedCornerShape(8.dp), color = VibeGreenDeep) {
                    Text(
                        "Xem ngay",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryPill(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .shadow(8.dp, RoundedCornerShape(8.dp), ambientColor = VibeGreen.copy(alpha = 0.18f), spotColor = VibeGreen.copy(alpha = 0.18f))
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = VibeGreenDark, modifier = Modifier.size(26.dp))
        }
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
    }
}

@Composable
fun EventCard(
    event: FanEvent,
    modifier: Modifier = Modifier,
    onClick: (FanEvent) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick(event) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(192.dp)
                    .background(eventBrush(event.title))
            ) {
                DateBadge(
                    month = if (event.time.contains("11")) "THANG 11" else "THANG 10",
                    day = when {
                        event.time.contains("05") -> "05"
                        event.time.contains("12") -> "12"
                        else -> "28"
                    },
                    modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconLine(Icons.Default.Schedule, event.time)
                IconLine(Icons.Default.LocationOn, "${event.venue}, ${event.city}")
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InterestedStack()
                    Surface(shape = RoundedCornerShape(8.dp), color = VibeGreen) {
                        Text(
                            "Quan tâm",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                            color = VibeGreenDeep,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DateBadge(month: String, day: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = Color.White.copy(alpha = 0.9f),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(month, color = VibeGreenDark, style = MaterialTheme.typography.labelSmall)
            Text(day, color = VibeGreenDeep, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun InterestedStack() {
    Box(modifier = Modifier.width(84.dp).height(32.dp)) {
        AvatarBubble("A", Modifier.align(Alignment.CenterStart))
        AvatarBubble("B", Modifier.align(Alignment.CenterStart).offset(x = 24.dp))
        Surface(
            modifier = Modifier.align(Alignment.CenterStart).offset(x = 48.dp).size(32.dp),
            shape = CircleShape,
            color = VibeSurfaceMuted,
            border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("+99", style = MaterialTheme.typography.labelMedium, color = VibeGreenDeep)
            }
        }
    }
}

@Composable
private fun AvatarBubble(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(eventBrush(label))
            .border(2.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun StockChip(status: EventStatus) {
    val (text, color, container) = when (status) {
        EventStatus.Available -> Triple("Còn vé", VibeGreenDark, VibeMint)
        EventStatus.LowStock -> Triple("Sắp hết", Color(0xFF78350F), MaterialTheme.colorScheme.tertiaryContainer)
        EventStatus.SoldOut -> Triple("Hết vé", MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.errorContainer)
    }
    Surface(shape = RoundedCornerShape(50), color = container, contentColor = color) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun IconLine(icon: ImageVector, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun Metric(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun Avatar(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(VibeMint),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.take(1).uppercase(),
            color = VibeGreenDeep,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TicketCode(code: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = VibeMintSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("QR / Mã vé", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(code, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Icon(Icons.Default.ConfirmationNumber, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun SectionHeader(title: String, action: String? = null, onAction: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        if (action != null) {
            OutlinedButton(
                onClick = { onAction?.invoke() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = VibeGreenDark),
                border = null
            ) {
                Text(action)
            }
        }
    }
}

@Composable
fun VenueZoneMap(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, VibeStroke)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().height(268.dp).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ZoneBlock("Zone A (VIP)", VibeGreen, Modifier.fillMaxWidth().height(80.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth().height(78.dp)) {
                ZoneBlock("Zone B", VibeBlueSoft, Modifier.weight(1f).fillMaxHeight(), contentColor = VibeBlue)
                ZoneBlock("Zone B", VibeBlueSoft, Modifier.weight(1f).fillMaxHeight(), contentColor = VibeBlue)
            }
            ZoneBlock("Zone C (Early Bird)", MaterialTheme.colorScheme.tertiaryContainer, Modifier.fillMaxWidth().height(64.dp), contentColor = MaterialTheme.colorScheme.onTertiaryContainer)
        }
    }
}

@Composable
private fun ZoneBlock(text: String, color: Color, modifier: Modifier, contentColor: Color = VibeGreenDeep) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(8.dp)).background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = contentColor, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun eventBrush(seed: String): Brush {
    val palettes = listOf(
        listOf(Color(0xFF004927), Color(0xFF2DC275), Color(0xFF72FCA9)),
        listOf(Color(0xFF1B1C1C), Color(0xFF006D3D), Color(0xFFF59E0B)),
        listOf(Color(0xFF1976D2), Color(0xFF2DC275), Color(0xFFD1FAE5)),
        listOf(Color(0xFF7B1FA2), Color(0xFFC2185B), Color(0xFFFDE68A))
    )
    return Brush.linearGradient(palettes[kotlin.math.abs(seed.hashCode()) % palettes.size])
}

@Composable
fun VerticalSpacer(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}
