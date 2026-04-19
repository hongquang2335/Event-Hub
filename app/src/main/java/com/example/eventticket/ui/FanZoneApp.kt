package com.example.eventticket.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eventticket.navigation.AppRoute
import com.example.eventticket.navigation.BottomTab
import com.example.eventticket.navigation.bottomTabs
import com.example.eventticket.ui.screens.auth.LoginRegisterScreen
import com.example.eventticket.ui.screens.auth.WelcomeScreen
import com.example.eventticket.ui.screens.booking.BookingFlowScreen
import com.example.eventticket.ui.screens.community.CommunityScreen
import com.example.eventticket.ui.screens.community.EventCommunityScreen
import com.example.eventticket.ui.screens.community.PostDetailScreen
import com.example.eventticket.ui.screens.home.EventDetailScreen
import com.example.eventticket.ui.screens.home.HomeScreen
import com.example.eventticket.ui.screens.profile.ProfileScreen
import com.example.eventticket.ui.screens.tickets.TicketsScreen
import com.example.eventticket.ui.theme.VibeGreen
import com.example.eventticket.ui.theme.VibeGreenDark
import com.example.eventticket.ui.theme.VibeGreenDeep
import com.example.eventticket.ui.theme.VibeMint
import com.example.eventticket.viewmodel.AppViewModel
import com.example.eventticket.viewmodel.AuthState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.MaterialTheme

@Composable
fun FanZoneApp(appViewModel: AppViewModel = viewModel()) {
    val authState by appViewModel.authState.collectAsState()
    val navController = rememberNavController()

    when (authState) {
        AuthState.Splash -> Text("FanZone")
        AuthState.Welcome -> WelcomeScreen(
            onLogin = appViewModel::showLogin,
            onGuest = appViewModel::continueAsGuest
        )
        AuthState.SignedOut -> LoginRegisterScreen(
            onLogin = appViewModel::login,
            onRegister = appViewModel::register
        )
        is AuthState.SignedIn -> MainNavHost(
            navController = navController,
            onSignOut = appViewModel::signOut
        )
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    onSignOut: () -> Unit
) {
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showBottomBar = currentRoute in bottomTabs.map { it.route }

    Scaffold(
        topBar = {
            if (showBottomBar) {
                FanZoneTopBar()
            }
        },
        bottomBar = {
            if (showBottomBar) {
                FanZoneBottomBar(navController = navController, currentRoute = currentRoute)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomTab.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomTab.Home.route) {
                HomeScreen(onEventClick = { navController.navigate(AppRoute.EventDetail.create(it)) })
            }
            composable(BottomTab.Community.route) {
                CommunityScreen(
                    onEventClick = { navController.navigate(AppRoute.EventDetail.create(it)) },
                    onPostClick = { navController.navigate(AppRoute.PostDetail.create(it)) }
                )
            }
            composable(BottomTab.Tickets.route) {
                TicketsScreen()
            }
            composable(BottomTab.Profile.route) {
                ProfileScreen(onSignOut = onSignOut)
            }
            composable(AppRoute.EventDetail.route) { entry ->
                val eventId = entry.arguments?.getString("eventId").orEmpty()
                EventDetailScreen(
                    eventId = eventId,
                    onBack = { navController.popBackStack() },
                    onBook = { navController.navigate(AppRoute.Booking.create(it)) }
                )
            }
            composable(AppRoute.EventCommunity.route) { entry ->
                val eventId = entry.arguments?.getString("eventId").orEmpty()
                EventCommunityScreen(
                    eventId = eventId,
                    onEventClick = { navController.navigate(AppRoute.EventDetail.create(it)) },
                    onPostClick = { navController.navigate(AppRoute.PostDetail.create(it)) }
                )
            }
            composable(AppRoute.PostDetail.route) { entry ->
                val postId = entry.arguments?.getString("postId").orEmpty()
                PostDetailScreen(
                    postId = postId,
                    onEventClick = { navController.navigate(AppRoute.EventDetail.create(it)) }
                )
            }
            composable(AppRoute.Booking.route) { entry ->
                val eventId = entry.arguments?.getString("eventId").orEmpty()
                BookingFlowScreen(
                    eventId = eventId,
                    onDone = {
                        navController.navigate(BottomTab.Tickets.route) {
                            popUpTo(BottomTab.Home.route)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FanZoneTopBar() {
    Surface(color = MaterialTheme.colorScheme.background, shadowElevation = 0.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(VibeMint),
                    contentAlignment = Alignment.Center
                ) {
                    Text("FZ", color = VibeGreenDeep, style = MaterialTheme.typography.labelLarge)
                }
                Text("FanZone", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            IconButton(
                onClick = {},
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surface)
            ) {
                Icon(Icons.Default.NotificationsNone, contentDescription = "Thong bao", tint = VibeGreenDark)
            }
        }
    }
}

@Composable
private fun FanZoneBottomBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth().height(97.dp),
        containerColor = Color.White,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0.dp)
    ) {
        bottomTabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label.uppercase(), style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VibeGreenDeep,
                    selectedTextColor = VibeGreenDeep,
                    indicatorColor = VibeGreen,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
