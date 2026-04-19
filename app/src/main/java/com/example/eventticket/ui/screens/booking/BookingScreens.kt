package com.example.eventticket.ui.screens.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventticket.model.FanEvent
import com.example.eventticket.model.TicketTier
import com.example.eventticket.model.toVnd
import com.example.eventticket.ui.components.StateContent
import com.example.eventticket.ui.components.TicketCode
import com.example.eventticket.ui.components.VenueZoneMap
import com.example.eventticket.ui.theme.VibeAmber
import com.example.eventticket.ui.theme.VibeGreen
import com.example.eventticket.ui.theme.VibeGreenDark
import com.example.eventticket.ui.theme.VibeGreenDeep
import com.example.eventticket.ui.theme.VibeMint
import com.example.eventticket.ui.theme.VibeStroke
import com.example.eventticket.viewmodel.HomeViewModel

@Composable
fun BookingFlowScreen(
    eventId: String,
    onDone: () -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val state by homeViewModel.state.collectAsState()
    StateContent(state = state) { data ->
        val event = data.events.firstOrNull { it.id == eventId } ?: data.events.first()
        BookingContent(event = event, onDone = onDone)
    }
}

@Composable
private fun BookingContent(event: FanEvent, onDone: () -> Unit) {
    var selectedTier by remember { mutableStateOf(event.tiers.first()) }
    var quantity by remember { mutableIntStateOf(1) }
    var completed by remember { mutableStateOf(false) }

    if (completed) {
        BookingSuccess(event = event, onDone = onDone)
        return
    }

    Scaffold(
        bottomBar = {
            CheckoutBar(
                quantity = quantity,
                total = selectedTier.price * quantity,
                onPay = { completed = true }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 196.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(event.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Sơ đồ khu vực", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    VenueZoneMap()
                }
            }
            item {
                Text("Chọn vé", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            items(event.tiers) { tier ->
                TierSelector(
                    tier = tier,
                    selected = selectedTier.id == tier.id,
                    quantity = if (selectedTier.id == tier.id) quantity else 0,
                    onClick = { selectedTier = tier },
                    onMinus = { quantity = (quantity - 1).coerceAtLeast(1) },
                    onPlus = { quantity = (quantity + 1).coerceAtMost(6) }
                )
            }
        }
    }
}

@Composable
private fun TierSelector(
    tier: TicketTier,
    selected: Boolean,
    quantity: Int,
    onClick: () -> Unit,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, if (selected) VibeGreen else VibeStroke),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 3.dp else 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(tier.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        if (tier.remaining <= 8 && tier.remaining > 0) {
                            Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.tertiaryContainer) {
                            Text("Sắp hết", modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), color = MaterialTheme.colorScheme.onTertiaryContainer, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                    Text(tier.benefits.joinToString(", "), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(tier.price.toVnd(), color = VibeGreenDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                if (selected) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VibeGreen)
                }
            }
            if (tier.remaining == 0) {
                Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                    Text("HẾT VÉ", modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                QuantityStepper(quantity = quantity.coerceAtLeast(if (selected) 1 else 0), enabled = selected, onMinus = onMinus, onPlus = onPlus)
            }
        }
    }
}

@Composable
private fun QuantityStepper(quantity: Int, enabled: Boolean, onMinus: () -> Unit, onPlus: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, VibeStroke)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(onClick = onMinus, enabled = enabled) {
                Icon(Icons.Default.Remove, contentDescription = "Giảm")
            }
            Text(quantity.toString(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            FilledIconButton(onClick = onPlus, enabled = enabled) {
                Icon(Icons.Default.Add, contentDescription = "Tăng")
            }
        }
    }
}

@Composable
private fun CheckoutBar(quantity: Int, total: Long, onPay: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 12.dp) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Tổng thanh toán ($quantity vé)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(total.toVnd(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = VibeGreenDeep)
                }
                Spacer(Modifier.width(12.dp))
            }
            Button(onClick = onPay, modifier = Modifier.fillMaxWidth()) {
                Text("Tiến hành thanh toán")
            }
        }
    }
}

@Composable
private fun BookingSuccess(event: FanEvent, onDone: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VibeGreen, modifier = Modifier.padding(bottom = 4.dp))
        Text("Đặt vé thành công", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(
            "Vé đã được thêm vào tab Vé. Bạn sẽ nhận thông báo trước khi sự kiện diễn ra.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TicketCode(code = "FZ-${event.id.take(3).uppercase()}-${System.currentTimeMillis().toString().takeLast(4)}")
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth()) {
            Text("Xem vé của tôi")
        }
    }
}
