package com.example.eventticket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventticket.data.FanZoneRepository
import com.example.eventticket.data.MockFanZoneRepository
import com.example.eventticket.model.PurchasedTicket
import com.example.eventticket.model.ResaleTicket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketsUiState(
    val tickets: List<PurchasedTicket>,
    val resaleTickets: List<ResaleTicket>,
    val selectedFilter: String = "Sắp diễn ra"
)

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
