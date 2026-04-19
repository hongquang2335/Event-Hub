package com.example.eventticket.ui.screens.tickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventticket.model.PurchasedTicket
import com.example.eventticket.model.ResaleState
import com.example.eventticket.model.ResaleTicket
import com.example.eventticket.model.TicketState
import com.example.eventticket.model.toVnd
import com.example.eventticket.ui.components.IconLine
import com.example.eventticket.ui.components.StateContent
import com.example.eventticket.ui.components.TicketCode
import com.example.eventticket.ui.theme.VibeGreenDark
import com.example.eventticket.viewmodel.TicketsUiState
import com.example.eventticket.viewmodel.TicketsViewModel

@Composable
fun TicketsScreen(viewModel: TicketsViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    StateContent(state = state) { data ->
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            TabRow(selectedTabIndex = selectedTab, containerColor = MaterialTheme.colorScheme.background) {
                listOf("Loại vé", "Vé đã đặt", "Resale").forEachIndexed { index, title ->
                    Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(title) })
                }
            }
            when (selectedTab) {
                0 -> TicketTypes(data)
                1 -> PurchasedTickets(data, viewModel::selectFilter)
                2 -> ResaleTickets(data)
            }
        }
    }
}

@Composable
private fun TicketTypes(data: TicketsUiState) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("So sánh quyền lợi", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        items(listOf("Standard", "Fanpit", "VIP")) { tier ->
            Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(tier, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(
                        when (tier) {
                            "VIP" -> "Ghế đẹp, lounge riêng, meet & greet, merch giới hạn."
                            "Fanpit" -> "Khu vực gần sân khấu, check-in ưu tiên, quà lưu niệm."
                            else -> "Vào cổng, QR check-in nhanh, quyền tham gia feed cộng đồng."
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text("Áp dụng cho ${data.tickets.size + data.resaleTickets.size} vé/sự kiện đang hiển thị.", color = VibeGreenDark)
                }
            }
        }
    }
}

@Composable
private fun PurchasedTickets(data: TicketsUiState, onFilter: (String) -> Unit) {
    val filters = listOf("Sắp diễn ra", "Đã sử dụng", "Đã hủy")
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = data.selectedFilter == filter,
                        onClick = { onFilter(filter) },
                        label = { Text(filter) }
                    )
                }
            }
        }
        val filtered = data.tickets.filter {
            when (data.selectedFilter) {
                "Đã sử dụng" -> it.state == TicketState.Used
                "Đã hủy" -> it.state == TicketState.Cancelled
                else -> it.state == TicketState.Upcoming
            }
        }
        items(filtered) { ticket -> PurchasedTicketCard(ticket) }
    }
}

@Composable
private fun PurchasedTicketCard(ticket: PurchasedTicket) {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(ticket.eventTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(ticket.tierName, color = MaterialTheme.colorScheme.primary)
                }
                AssistChip(onClick = {}, label = { Text(ticket.state.label()) })
            }
            IconLine(Icons.Default.Schedule, ticket.time)
            IconLine(Icons.Default.LocationOn, ticket.venue)
            TicketCode(ticket.qrCode)
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Đăng ký bán lại")
            }
        }
    }
}

@Composable
private fun ResaleTickets(data: TicketsUiState) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Chợ vé resale", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Ai mua trước sẽ sở hữu trước. Khi nối Firestore, mua nhanh cần chạy transaction khóa listing.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        items(data.resaleTickets) { resale -> ResaleCard(resale) }
    }
}

@Composable
private fun ResaleCard(resale: ResaleTicket) {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(resale.eventTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                AssistChip(onClick = {}, label = { Text(resale.state.label()) })
            }
            Text("${resale.tierName} - ${resale.price.toVnd()}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            resale.warning?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(
                enabled = resale.state == ResaleState.OnSale,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mua nhanh")
            }
        }
    }
}

private fun ResaleState.label(): String = when (this) {
    ResaleState.PendingApproval -> "Chờ duyệt"
    ResaleState.OnSale -> "Đang bán"
    ResaleState.Sold -> "Đã bán"
}

private fun TicketState.label(): String = when (this) {
    TicketState.Upcoming -> "Sắp diễn ra"
    TicketState.Used -> "Đã sử dụng"
    TicketState.Cancelled -> "Đã hủy"
}
