package com.example.myapplication.model

import androidx.annotation.DrawableRes

data class Category(
    val id: String,
    val label: String,
    val emoji: String
)

data class Event(
    val id: String,
    val title: String,
    val subtitle: String,
    val schedule: String,
    val venue: String,
    val city: String,
    val description: String,
    val artists: List<String>,
    val timeline: List<EventMoment>,
    val notices: List<String>,
    @param:DrawableRes val imageRes: Int
)

data class EventMoment(
    val time: String,
    val title: String
)

data class TicketTier(
    val id: String,
    val eventId: String,
    val name: String,
    val benefits: String,
    val price: Int,
    val status: TierStatus
)

enum class TierStatus {
    AVAILABLE,
    LIMITED,
    SOLD_OUT
}

data class CommunityPost(
    val id: String,
    val author: String,
    val role: String,
    val topic: String,
    val content: String,
    val likes: Int,
    val comments: Int,
    @param:DrawableRes val imageRes: Int?
)

data class TicketWalletItem(
    val id: String,
    val eventId: String,
    val eventTitle: String,
    val seatLabel: String,
    val schedule: String,
    val venue: String,
    val qrCode: String,
    val status: TicketStatus
)

enum class TicketStatus {
    UPCOMING,
    COMPLETED,
    CANCELLED
}

data class PaymentMethod(
    val id: String,
    val label: String,
    val subtitle: String
)

data class SupportShortcut(
    val id: String,
    val title: String
)

data class UserProfile(
    val name: String,
    val membership: String,
    val city: String
)
