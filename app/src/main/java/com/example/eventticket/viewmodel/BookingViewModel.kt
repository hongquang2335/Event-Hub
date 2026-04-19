package com.example.eventticket.viewmodel

import androidx.lifecycle.ViewModel
import com.example.eventticket.model.BookingDraft
import com.example.eventticket.model.FanEvent
import com.example.eventticket.model.TicketTier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
