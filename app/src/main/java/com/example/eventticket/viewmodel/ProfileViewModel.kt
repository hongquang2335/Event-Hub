package com.example.eventticket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventticket.data.FanZoneRepository
import com.example.eventticket.data.MockFanZoneRepository
import com.example.eventticket.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
