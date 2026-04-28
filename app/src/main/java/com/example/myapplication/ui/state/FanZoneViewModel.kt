package com.example.myapplication.ui.state

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.FanZoneRepository
import com.example.myapplication.model.TicketStatus
import com.example.myapplication.model.TicketWalletItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FanZoneViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        FanZoneUiState(
            user = FanZoneRepository.user,
            events = FanZoneRepository.events,
            tiers = FanZoneRepository.tiers,
            posts = FanZoneRepository.posts,
            walletItems = FanZoneRepository.walletSeed,
            paymentMethods = FanZoneRepository.paymentMethods,
            supportShortcuts = FanZoneRepository.supportShortcuts,
            selectedEventId = FanZoneRepository.events.first().id,
            selectedPaymentMethod = FanZoneRepository.paymentMethods.first().id,
            unreadSupportCount = 2,
            tierQuantities = emptyMap()
        )
    )
    val uiState: StateFlow<FanZoneUiState> = _uiState.asStateFlow()

    fun selectEvent(eventId: String) {
        _uiState.update { it.copy(selectedEventId = eventId) }
    }

    fun selectPaymentMethod(methodId: String) {
        _uiState.update { it.copy(selectedPaymentMethod = methodId) }
    }

    fun setTierQuantity(tierId: String, quantity: Int) {
        _uiState.update { state ->
            state.copy(
                tierQuantities = state.tierQuantities.toMutableMap().apply {
                    put(tierId, quantity.coerceAtLeast(0))
                }
            )
        }
    }

    fun clearTicketSelection() {
        _uiState.update { it.copy(tierQuantities = emptyMap()) }
    }

    fun confirmPurchase(): TicketWalletItem {
        val state = _uiState.value
        val seatLabel = state.tiersForSelectedEvent
            .filter { (state.tierQuantities[it.id] ?: 0) > 0 }
            .joinToString { tier -> "${tier.name} x${state.tierQuantities[tier.id] ?: 0}" }

        val ticket = TicketWalletItem(
            id = "ticket-${state.walletItems.size + 1}",
            eventId = state.selectedEvent.id,
            eventTitle = state.selectedEvent.title,
            seatLabel = seatLabel,
            schedule = state.selectedEvent.schedule,
            venue = "${state.selectedEvent.venue}, ${state.selectedEvent.city}",
            qrCode = "QR-${state.selectedEvent.id.uppercase()}-${state.walletItems.size + 1}",
            status = TicketStatus.UPCOMING
        )

        _uiState.update {
            it.copy(
                walletItems = listOf(ticket) + it.walletItems,
                latestPurchasedTicketId = ticket.id,
                tierQuantities = emptyMap()
            )
        }
        return ticket
    }
}
