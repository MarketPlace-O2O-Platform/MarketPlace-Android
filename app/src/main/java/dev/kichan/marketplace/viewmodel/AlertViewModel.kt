package dev.kichan.marketplace.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.NotificationRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlertUiState(
    val notifications: List<NotificationRes> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val filterType: String? = null
)

class AlertViewModel : ViewModel() {
    private val notificationRepository = RepositoryProvider.provideNotificationRepository()

    private val _uiState = MutableStateFlow(AlertUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun setFilterType(type: String?) {
        _uiState.update { it.copy(filterType = type) }
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val currentType = _uiState.value.filterType
                val response = notificationRepository.getNotificationList(currentType)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            notifications = response.body()!!.response.notificationResList
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "알림을 불러오는데 실패했습니다.") }
                }
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
                _uiState.update { it.copy(isLoading = false, error = "알림을 불러오는 중 오류가 발생했습니다.") }
            }
        }
    }

    fun allRead() {
        viewModelScope.launch {
            notificationRepository.readNotificationALl()
        }
    }
}