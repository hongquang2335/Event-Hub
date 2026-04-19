package com.example.eventticket.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.eventticket.ui.theme.VibeBlue
import com.example.eventticket.ui.theme.VibeBlueSoft
import com.example.eventticket.ui.theme.VibeGreen
import com.example.eventticket.ui.theme.VibeGreenDeep
import com.example.eventticket.ui.theme.VibeMintSoft
import com.example.eventticket.ui.theme.VibeStroke

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
fun VenueZoneMap(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, VibeStroke)
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
