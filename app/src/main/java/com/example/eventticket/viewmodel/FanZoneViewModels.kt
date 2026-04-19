package com.example.eventticket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventticket.data.FanZoneRepository
import com.example.eventticket.data.MockFanZoneRepository
import com.example.eventticket.model.BookingDraft
import com.example.eventticket.model.CommunityPost
import com.example.eventticket.model.FanEvent
import com.example.eventticket.model.PurchasedTicket
import com.example.eventticket.model.ResaleTicket
import com.example.eventticket.model.TicketTier
import com.example.eventticket.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>
    data class Ready<T>(val data: T) : ScreenState<T>
    data class Empty(val message: String) : ScreenState<Nothing>
    data class Error(val message: String) : ScreenState<Nothing>
}

sealed interface AuthState {
    data object Splash : AuthState
    data object Welcome : AuthState
    data object SignedOut : AuthState
    data class SignedIn(val userName: String) : AuthState
}

data class HomeUiState(
    val events: List<FanEvent>,
    val searchQuery: String = "",
    val selectedSection: String = "Xu hướng"
) {
    val filteredEvents: List<FanEvent> =
        events.filter {
            searchQuery.isBlank() ||
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.city.contains(searchQuery, ignoreCase = true) ||
                it.tags.any { tag -> tag.contains(searchQuery, ignoreCase = true) }
        }
}

data class CommunityUiState(
    val posts: List<CommunityPost>,
    val eventFilter: String? = null
)

data class TicketsUiState(
    val tickets: List<PurchasedTicket>,
    val resaleTickets: List<ResaleTicket>,
    val selectedFilter: String = "Sắp diễn ra"
)

class AppViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Splash)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        _authState.value = AuthState.Welcome
    }

    fun continueAsGuest() {
        _authState.value = AuthState.SignedIn("FanZone Guest")
    }

    fun showLogin() {
        _authState.value = AuthState.SignedOut
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.SignedIn(email.substringBefore('@').ifBlank { "FanZone User" })
    }

    fun register(name: String, email: String, password: String) {
        _authState.value = AuthState.SignedIn(name.ifBlank { email.substringBefore('@') })
    }

    fun signOut() {
        _authState.value = AuthState.Welcome
    }
}

class HomeViewModel(
    private val repository: FanZoneRepository = MockFanZoneRepository()
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState<HomeUiState>>(ScreenState.Loading)
    val state: StateFlow<ScreenState<HomeUiState>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.featuredEvents().collect { events ->
                _state.value = if (events.isEmpty()) {
                    ScreenState.Empty("Chưa có sự kiện phù hợp.")
                } else {
                    ScreenState.Ready(HomeUiState(events))
                }
            }
        }
    }

    fun updateSearch(query: String) {
        val current = (_state.value as? ScreenState.Ready)?.data ?: return
        _state.value = ScreenState.Ready(current.copy(searchQuery = query))
    }

    fun selectSection(section: String) {
        val current = (_state.value as? ScreenState.Ready)?.data ?: return
        _state.value = ScreenState.Ready(current.copy(selectedSection = section))
    }
}

class CommunityViewModel(
    private val repository: FanZoneRepository = MockFanZoneRepository()
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState<CommunityUiState>>(ScreenState.Loading)
    val state: StateFlow<ScreenState<CommunityUiState>> = _state.asStateFlow()

    init {
        loadPosts(null)
    }

    fun loadPosts(eventId: String?) {
        viewModelScope.launch {
            repository.communityPosts(eventId).collect { posts ->
                _state.value = if (posts.isEmpty()) {
                    ScreenState.Empty("Chưa có bài đăng cho sự kiện này.")
                } else {
                    ScreenState.Ready(CommunityUiState(posts = posts, eventFilter = eventId))
                }
            }
        }
    }
}

class TicketsViewModel(
    private val repository: FanZoneRepository = MockFanZoneRepository()
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState<TicketsUiState>>(ScreenState.Loading)
    val state: StateFlow<ScreenState<TicketsUiState>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.purchasedTickets().collect { tickets ->
                repository.resaleTickets().collect { resale ->
                    _state.value = ScreenState.Ready(TicketsUiState(tickets = tickets, resaleTickets = resale))
                }
            }
        }
    }

    fun selectFilter(filter: String) {
        val current = (_state.value as? ScreenState.Ready)?.data ?: return
        _state.value = ScreenState.Ready(current.copy(selectedFilter = filter))
    }
}

class ProfileViewModel(
    private val repository: FanZoneRepository = MockFanZoneRepository()
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState<UserProfile>>(ScreenState.Loading)
    val state: StateFlow<ScreenState<UserProfile>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.currentProfile().collect { profile ->
                _state.value = ScreenState.Ready(profile)
            }
        }
    }
}

class BookingViewModel : ViewModel() {
    private val _draft = MutableStateFlow<BookingDraft?>(null)
    val draft: StateFlow<BookingDraft?> = _draft.asStateFlow()

    fun start(event: FanEvent, tier: TicketTier, quantity: Int) {
        _draft.value = BookingDraft(event = event, tier = tier, quantity = quantity)
    }

    fun updateQuantity(quantity: Int) {
        val current = _draft.value ?: return
        _draft.value = current.copy(quantity = quantity.coerceIn(1, 6))
    }

    fun clear() {
        _draft.value = null
    }
}
