package com.example.myapplication.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.feature.booking.BookingScreen
import com.example.myapplication.feature.checkout.CheckoutScreen
import com.example.myapplication.feature.community.CommunityScreen
import com.example.myapplication.feature.event.EventDetailScreen
import com.example.myapplication.feature.home.HomeScreen
import com.example.myapplication.feature.profile.ProfileScreen
import com.example.myapplication.feature.success.PurchaseSuccessScreen
import com.example.myapplication.feature.support.SupportScreen
import com.example.myapplication.feature.tickets.TicketWalletScreen
import com.example.myapplication.navigation.AppDestination
import com.example.myapplication.navigation.bottomDestinations
import com.example.myapplication.ui.components.AppBottomBar
import com.example.myapplication.ui.state.FanZoneViewModel

@Composable
fun FanZoneApp(
    viewModel: FanZoneViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val currentRoute = currentDestination?.route
    val showBottomBar = currentRoute in bottomDestinations.map { it.route }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                if (showBottomBar) {
                    AppBottomBar(
                        items = bottomDestinations,
                        currentRoute = currentRoute
                    ) { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestination.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(AppDestination.Home.route) {
                    HomeScreen(
                        event = uiState.selectedEvent,
                        events = uiState.events,
                        categories = com.example.myapplication.data.FanZoneRepository.categories,
                        onOpenEvent = { eventId ->
                            viewModel.selectEvent(eventId)
                            navController.navigate(AppDestination.EventDetail.create(eventId))
                        },
                        onOpenCommunity = { navController.navigate(AppDestination.Community.route) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(AppDestination.Community.route) {
                    CommunityScreen(
                        posts = uiState.posts,
                        onOpenEvent = {
                            navController.navigate(AppDestination.EventDetail.create(uiState.selectedEvent.id))
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(AppDestination.Tickets.route) {
                    TicketWalletScreen(
                        tickets = uiState.walletItems,
                        onOpenEvent = { eventId ->
                            viewModel.selectEvent(eventId)
                            navController.navigate(AppDestination.EventDetail.create(eventId))
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(AppDestination.Profile.route) {
                    ProfileScreen(
                        user = uiState.user,
                        unreadSupport = uiState.unreadSupportCount,
                        onOpenSupport = { navController.navigate(AppDestination.Support.route) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(AppDestination.Support.route) {
                    SupportScreen(
                        supportShortcuts = uiState.supportShortcuts,
                        unreadSupport = uiState.unreadSupportCount,
                        onBack = { navController.popBackStack() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(
                    route = AppDestination.EventDetail.route,
                    arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                ) { entry ->
                    entry.arguments?.getString("eventId")?.let(viewModel::selectEvent)
                    EventDetailScreen(
                        event = uiState.selectedEvent,
                        tiers = uiState.tiersForSelectedEvent,
                        onBack = { navController.popBackStack() },
                        onBuyNow = { navController.navigate(AppDestination.Booking.create(uiState.selectedEvent.id)) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(
                    route = AppDestination.Booking.route,
                    arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                ) { entry ->
                    entry.arguments?.getString("eventId")?.let(viewModel::selectEvent)
                    BookingScreen(
                        event = uiState.selectedEvent,
                        tiers = uiState.tiersForSelectedEvent,
                        quantities = uiState.tierQuantities,
                        onBack = { navController.popBackStack() },
                        onChangeQuantity = viewModel::setTierQuantity,
                        onContinue = { navController.navigate(AppDestination.Checkout.create(uiState.selectedEvent.id)) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(
                    route = AppDestination.Checkout.route,
                    arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                ) { entry ->
                    entry.arguments?.getString("eventId")?.let(viewModel::selectEvent)
                    CheckoutScreen(
                        event = uiState.selectedEvent,
                        tiers = uiState.tiersForSelectedEvent,
                        quantities = uiState.tierQuantities,
                        paymentMethods = uiState.paymentMethods,
                        selectedPaymentMethod = uiState.selectedPaymentMethod,
                        onBack = { navController.popBackStack() },
                        onSelectPayment = viewModel::selectPaymentMethod,
                        onConfirm = {
                            viewModel.confirmPurchase()
                            navController.navigate(AppDestination.Success.route) {
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(AppDestination.Success.route) {
                    PurchaseSuccessScreen(
                        ticket = uiState.latestPurchasedTicket,
                        onOpenWallet = {
                            navController.navigate(AppDestination.Tickets.route) {
                                popUpTo(AppDestination.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        onGoHome = {
                            navController.navigate(AppDestination.Home.route) {
                                popUpTo(AppDestination.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
