package com.example.myapplication.ui.state

import com.example.myapplication.model.CommunityPost
import com.example.myapplication.model.Event
import com.example.myapplication.model.PaymentMethod
import com.example.myapplication.model.SupportShortcut
import com.example.myapplication.model.TicketTier
import com.example.myapplication.model.TicketWalletItem
import com.example.myapplication.model.UserProfile

data class FanZoneUiState(
    val user: UserProfile,
    val events: List<Event>,
    val tiers: List<TicketTier>,
    val posts: List<CommunityPost>,
    val walletItems: List<TicketWalletItem>,
    val paymentMethods: List<PaymentMethod>,
    val supportShortcuts: List<SupportShortcut>,
    val selectedEventId: String,
    val selectedPaymentMethod: String,
    val unreadSupportCount: Int,
    val tierQuantities: Map<String, Int>,
    val latestPurchasedTicketId: String? = null
) {
    val selectedEvent: Event
        get() = events.first { it.id == selectedEventId }

    val tiersForSelectedEvent: List<TicketTier>
        get() = tiers.filter { it.eventId == selectedEventId }

    val latestPurchasedTicket: TicketWalletItem?
        get() = latestPurchasedTicketId?.let { ticketId -> walletItems.find { it.id == ticketId } }
}
