package com.example.eventticket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventticket.data.FanZoneRepository
import com.example.eventticket.data.MockFanZoneRepository
import com.example.eventticket.model.CommunityPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CommunityUiState(
    val posts: List<CommunityPost>,
    val eventFilter: String? = null
)

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
