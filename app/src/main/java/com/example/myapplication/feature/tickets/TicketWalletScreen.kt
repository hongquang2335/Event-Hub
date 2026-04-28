package com.example.myapplication.feature.tickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.TicketStatus
import com.example.myapplication.model.TicketWalletItem
import com.example.myapplication.ui.components.EmptyStateCard
import com.example.myapplication.ui.components.SectionHeader
import com.example.myapplication.ui.components.TicketCard
import com.example.myapplication.ui.components.TicketStatusFilter

@Composable
fun TicketWalletScreen(
    tickets: List<TicketWalletItem>,
    onOpenEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(TicketStatus.UPCOMING) }

    BoxWithConstraints(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        val isExpanded = maxWidth >= 720.dp
        val filtered = tickets.filter { it.status == selectedTab }

        LazyColumn(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { SectionHeader("Ve cua toi", "Quan ly ve va QR check-in") }
            item { TicketStatusFilter(selectedTab, onSelect = { selectedTab = it }) }

            if (filtered.isEmpty()) {
                item { EmptyStateCard("Khong co ve o trang thai nay", "Mua mot su kien moi de lap day vi ve.") }
            } else if (isExpanded) {
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        filtered.forEach { item ->
                            Box(modifier = Modifier.weight(1f)) { TicketCard(item, onOpenEvent) }
                        }
                    }
                }
            } else {
                items(filtered) { item -> TicketCard(item, onOpenEvent) }
            }
        }
    }
}
