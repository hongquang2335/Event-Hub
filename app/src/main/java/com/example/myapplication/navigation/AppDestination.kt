package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppDestination(val route: String) {
    data object Home : AppDestination("home")
    data object Community : AppDestination("community")
    data object Tickets : AppDestination("tickets")
    data object Profile : AppDestination("profile")
    data object Support : AppDestination("support")
    data object EventDetail : AppDestination("event/{eventId}") {
        fun create(eventId: String) = "event/$eventId"
    }
    data object Booking : AppDestination("booking/{eventId}") {
        fun create(eventId: String) = "booking/$eventId"
    }
    data object Checkout : AppDestination("checkout/{eventId}") {
        fun create(eventId: String) = "checkout/$eventId"
    }
    data object Success : AppDestination("success")
}

data class BottomDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomDestinations = listOf(
    BottomDestination(AppDestination.Home.route, "Trang chu", Icons.Default.Home),
    BottomDestination(AppDestination.Community.route, "Cong dong", Icons.Default.ChatBubbleOutline),
    BottomDestination(AppDestination.Tickets.route, "Ve cua toi", Icons.Default.ConfirmationNumber),
    BottomDestination(AppDestination.Profile.route, "Tai khoan", Icons.Default.AccountCircle)
)
